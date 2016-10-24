package track.container;

import java.lang.reflect.Field;
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
    private List<Bean> beans;
    private Map<String, Object> objByName;
    private Map<String, Object> objByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
        objByClassName = new HashMap<>();
        objByName = new HashMap<>();
    }

    private String getSetter(String propertyName) {
        StringBuilder builder = new StringBuilder("set");
        builder.append(propertyName.substring(0, 1).toUpperCase());
        builder.append(propertyName.substring(1));
        return builder.toString();
    }

    Object getPrimitive(Class type, String value) {
        if (type == Integer.TYPE || type == Integer.class) {
            return new Integer(value);
        } else if (type == Float.TYPE || type == Integer.class) {
            return new Float(value);
        } else if (type == Double.TYPE || type == Double.class) {
            return new Double(value);
        } else if (type == Boolean.TYPE || type == Boolean.class) {
            return new Boolean(value);
        } else if (type == Byte.TYPE || type == Byte.class) {
            return new Byte(value);
        } else if (type == Short.TYPE || type == Short.class) {
            return new Short(value);
        } else if (type == Long.TYPE || type == Long.class) {
            return new Long(value);
        } else { //char
            return value.charAt(0);
        }
    }

    private Object createInstance(Bean bean) {
        try {
            Class singleton = Class.forName(bean.getClassName());
            Object instance = singleton.newInstance();
            for (Property property : bean.getProperties()) {
                Field field = singleton.getDeclaredField(property.getName());
                field.setAccessible(true);
                Class type = field.getType();
                Object fieldValue = null;
                if (property.getVal() != null) {
                    if (type.isPrimitive()) {
                        fieldValue = getPrimitive(type, property.getVal());
                    } else {
                        fieldValue = type.getConstructor(String.class).newInstance(property.getVal());
                    }
                } else if (property.getRef() != null) { // reference type
                    fieldValue = getById(property.getRef());
                }
                Method setter = singleton.getMethod(getSetter(property.getName()), type);
                setter.invoke(instance, fieldValue);
            }
            objByClassName.put(bean.getClassName(), instance);
            objByName.put(bean.getId(), instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */

    public Object getById(String id) {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        } else {
            for (Bean bean : beans) {
                if (bean.getId().equals(id)) {
                    return createInstance(bean);
                }
            }
            return null;
        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        } else {
            for (Bean bean : beans) {
                if (bean.getClassName().equals(className)) {
                    return createInstance(bean);
                }
            }
            return null;
        }
    }
}
