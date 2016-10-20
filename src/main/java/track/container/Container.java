package track.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import track.container.config.Bean;
import track.container.config.Property;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private List<Bean> beans;
    private Map<String, Object> objById;
    private Map<String, Object> objByClass;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
        objById = new HashMap<>();
        objByClass = new HashMap<>();
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        if (objById.containsKey(id)) {
            return objById.get(id);
        } else {
            Bean bean = null;
            for (Bean b : beans) {
                if (b.getId().equals(id)) {
                    bean = b;
                    break;
                }
            }
            if (bean == null) {
                return null;
            }
            return beanToInstance(bean);
        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        if (objByClass.containsKey(className)) {
            return objByClass.get(className);
        } else {
            Bean bean = null;
            for (Bean b : beans) {
                if (b.getClassName().equals(className)) {
                    bean = b;
                    break;
                }
            }
            if (bean == null) {
                return null;
            }
            return beanToInstance(bean);
        }
    }

    private Object beanToInstance(Bean bean) {
        try {
            Class clazz;
            clazz = Class.forName(bean.getClassName());
            Object instance = clazz.newInstance();
            for (Property property : bean.getProperties()) {
                String methodName = getSetterName(property.getName());
                Field field = clazz.getDeclaredField(property.getName());
                field.setAccessible(true);
                Method method = clazz.getMethod(methodName, field.getType());
                if (property.getVal() != null) {
                    Object paramInstance;
                    if (field.getType().isPrimitive()) {
                        paramInstance = primitiveStringToInstance(field.getType(), property.getVal());
                    } else {
                        paramInstance = field.getType().getConstructor(String.class)
                                .newInstance(property.getVal());
                    }
                    method.invoke(instance, paramInstance);
                }
                if (property.getRef() != null) {
                    method.invoke(instance, objById.getOrDefault(property.getRef(),
                            getById(property.getRef())));
                }
            }
            objByClass.put(bean.getClassName(), instance);
            objById.put(bean.getId(), instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSetterName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() +
                propertyName.substring(1, propertyName.length());
    }

/*    private boolean isPrimitive(Class clazz) {
        return Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,
                Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE)
                .contains(clazz);
    }
*/
    private Object primitiveStringToInstance(Class type, String value) {
        String typeName = type.getTypeName();
        switch (typeName) {
            case("byte") : return new Byte(value);
            case("short") : return new Short(value);
            case("int") : return new Integer(value);
            case("long") : return new Long(value);
            case("float") : return new Float(value);
            case("double") : return new Double(value);
            case("boolean") : return Boolean.valueOf(value);
            case("char") : return value.charAt(0);
            default : return null;
        }
    }
}
