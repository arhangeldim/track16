package track.container;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private Map<String, Bean> beansById = new HashMap();
    private Map<String, Bean> beansByClassName = new HashMap();
    private Map<Bean, Object> objByBean = new HashMap();

    public Container(List<Bean> beans) {
        for (Bean bean: beans) {
            this.beansById.put(bean.getId(), bean);
            this.beansByClassName.put(bean.getClassName(), bean);
        }
    }

    public Container(String configFileName) throws InvalidConfigurationException {
        this((new JsonConfigReader()).parseBeans(new File(Main.class.getClassLoader()
                .getResource(configFileName).getFile())));
    }

    private String getSetMethodName(String fieldName) {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    private void createObject(Bean bean) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchFieldException,
            NoSuchMethodException, InvocationTargetException {
        Class clazz = Class.forName(bean.getClassName());
        Object object = clazz.newInstance();
        objByBean.put(bean, object);

        for (Property property: bean.getProperties().values()) {
            Field field = clazz.getDeclaredField(property.getName());
            Class type = field.getType();
            Method set = clazz.getMethod(getSetMethodName(property.getName()), type);

            switch (property.getType()) {
                case VAL:
                    PropertyEditor editor = PropertyEditorManager.findEditor(type);
                    editor.setAsText(property.getValue());
                    set.invoke(object, editor.getValue());
                    break;
                case REF:
                    if (!objByBean.containsKey(beansById.get(property.getValue()))) {
                        createObject(beansById.get(property.getValue()));
                    }
                    set.invoke(object, objByBean.get(beansById.get(property.getValue())));
                    break;
                default:
                    break;
            }
        }
    }

    private Object getByBean(Bean bean) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, NoSuchMethodException,
            NoSuchFieldException, InvocationTargetException {
        if (bean == null) {
            return null;
        }

        if (!objByBean.containsKey(bean)) {
            createObject(bean);
        }

        return objByBean.get(bean);
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException,
            NoSuchFieldException, InvocationTargetException {
        return getByBean(beansById.get(id));
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException,
            NoSuchFieldException, InvocationTargetException {

        return getByBean(beansByClassName.get(className));
    }
}
