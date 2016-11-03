package track.container;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.Property;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private Map<Bean, Object> objectsByBean = new HashMap();
    private Map<String, Bean> beansById = new HashMap();
    private Map<String, Bean> beansByClass = new HashMap();

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        for (Bean bean: beans) {
            this.beansById.put(bean.getId(), bean);
            this.beansByClass.put(bean.getClassName(), bean);
        }
    }


    /**
     *  Создать объект по бину
     */
    private void createObject(Bean bean) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException,
            InstantiationException, ClassNotFoundException, InvocationTargetException {
        Class classOfBean = Class.forName(bean.getClassName());
        Object object = classOfBean.newInstance();
        objectsByBean.put(bean, object);
        for (Property property: bean.getProperties().values()) {
            Field field = classOfBean.getDeclaredField(property.getName());
            Class type = field.getType();
            Method setMethod = classOfBean.getMethod("set" + Character.toUpperCase(property.getName().charAt(0)) +
                    property.getName().substring(1), type);
            switch (property.getType()) {
                case VAL:
                    PropertyEditor editor = PropertyEditorManager.findEditor(type);
                    editor.setAsText(property.getValue());
                    setMethod.invoke(object, editor.getValue());
                    break;
                case REF:
                    if (!objectsByBean.containsKey(beansById.get(property.getValue()))) {
                        createObject(beansById.get(property.getValue()));
                    }
                    setMethod.invoke(object, objectsByBean.get(beansById.get(property.getValue())));
                    break;
            }
        }
    }

    /**
     *  Вернуть объект по бину
     */
    private Object getByBean(Bean bean) throws NoSuchMethodException, IllegalAccessException, InstantiationException,
            ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        if (bean == null) {
            return null;
        }
        if (!objectsByBean.containsKey(bean)) {
            createObject(bean);
        }
        return objectsByBean.get(bean);
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws NoSuchMethodException, IllegalAccessException, InstantiationException,
            NoSuchFieldException, InvocationTargetException, ClassNotFoundException {
        return getByBean(beansById.get(id));
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, NoSuchFieldException, InvocationTargetException, ClassNotFoundException {
        return getByBean(beansByClass.get(className));
    }
}
