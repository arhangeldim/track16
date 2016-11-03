package track.container;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import static org.apache.log4j.config.PropertyPrinter.capitalize;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Bean> beanById;
    private Map<String, Bean> beanByClassName;
    private Map<String, Object> objById;
    private Map<String, Object> objByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        beanById = new HashMap<>();
        beanByClassName = new HashMap<>();
        objById = new HashMap<>();
        objByClassName = new HashMap<>();
        for (Bean bean : beans) {
            beanById.put(bean.getId(), bean);
            beanByClassName.put(bean.getClassName(), bean);
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws InvalidConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Object object = objById.get(id);
        if (object == null) {
            Bean bean = beanById.get(id);
            if (bean != null) {
                object = createObject(bean);
            } else {
                throw new InvalidConfigurationException("Bean without id");
            }
        }

        return object;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws InvalidConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Object object = objByClassName.get(className);

        if (object == null) {
            Bean bean = beanByClassName.get(className);
            if (bean != null) {
                object = createObject(bean);
            } else {
                throw new InvalidConfigurationException("Bean without class");
            }
        }

        return object;
    }

    private Object createObject(Bean bean) throws ValueException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = Class.forName(bean.getClassName());
        Object object = clazz.newInstance();
        objById.put(bean.getId(), object);
        for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
            Property property = entry.getValue();
            Field field = clazz.getDeclaredField(property.getName());
            Class type = field.getType();
            Method set = clazz.getMethod(getSetName(property.getName()), type);
            if (property.getType() == ValueType.VAL) {
                set.invoke(object, Integer.parseInt(property.getValue()));
            } else if (property.getType() == ValueType.REF) {
                if (!objById.containsKey(beanById.get(property.getValue()))) {
                    createObject(beanById.get(property.getValue()));
                }
                set.invoke(object, objById.get(beanById.get(property.getValue())));
            }

        }
        return object;
    }

    private String getSetName(String line) {
        return "set" + Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
