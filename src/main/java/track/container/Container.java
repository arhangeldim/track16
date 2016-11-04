package track.container;


import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Object> objById;
    private Map<String, Object> objByClassName;
    private List<Bean> beansById;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        beansById = beans;
        objById = new HashMap<>();
        objByClassName = new HashMap<>();
    }

    private Bean getBeanById(String id) {
        for (Bean bean : beansById) {
            if (bean.getId().equals(id))
                return bean;
        }
        return null;
    }

    private Bean getBeanByClassName(String className) {
        for (Bean bean : beansById) {
            if (bean.getClassName().equals(className)) {
                return bean;
            }
        }
        return null;
    }

    /**
     * Сохраняет объект по bean'у
     * @param bean
     * @param object
     */
    public void saveObject(Bean bean, Object object) {
        objByClassName.put(bean.getClassName(), object);
        objById.put(bean.getId(), object);
    }

    /**
     * Обертка над makeInstanceUnwrapped. Перехватывает часть исключений и оборочанивает другими.
     * @param bean
     * @return Object созданный по bean'у
     * @throws InvocationTargetException
     * @throws IllegalClassFormatException
     */
    public Object makeInstance(Bean bean)
            throws InvocationTargetException,
            IllegalClassFormatException {
        try {
            return makeInstanceUnwrapped(bean);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class " + bean.getClassName() + " not found");
        } catch (InstantiationException ex) {
            IllegalClassFormatException e = new IllegalClassFormatException("Class " + bean.getClassName() + " must have default constructor");
            e.addSuppressed(ex);
            throw e;
        } catch (IllegalAccessException ex) {
            IllegalClassFormatException e = new IllegalClassFormatException("Class has IllegalAccessException " + bean.getClassName());
            e.addSuppressed(ex);
            throw e;
        }
        return null;
    }

    /**
     * @param bean
     * @return объект , собранный по bean'у
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    private Object makeInstanceUnwrapped(Bean bean)
            throws ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException,
            IllegalClassFormatException {
        Class clazz = Class.forName(bean.getClassName());
        Object obj = clazz.newInstance();
        saveObject(bean, obj);
        Map<String, Property> properties = bean.getProperties();
        for (Map.Entry<String, Property> propEntry : properties.entrySet()) {
            try {
                Property property = propEntry.getValue();
                if (propEntry.getValue().getType() == ValueType.VAL) {
                    Class type = getValSetterMethodParamType(clazz, property);
                    Method m = clazz.getMethod(getSetterMethodName(propEntry.getKey()), type);
                    m.invoke(obj, getValSetterMethodParam(type, property.getValue()));
                } else {
                    Object paramObj = getById(property.getValue());
                    Class type = paramObj.getClass();
                    Method m = clazz.getMethod(getSetterMethodName(propEntry.getKey()), type);
                    m.invoke(obj, paramObj);
                }
            } catch (NoSuchFieldException | NoSuchMethodException ex) {
                System.out.println("Property " + propEntry.getKey() + " isn't used");
            }
        }
        return obj;
    }

    private String getSetterMethodName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * Нужен для определения типа параметра для set<PropertyName> метода.
     *
     * @param clazz    класс, для которого ищется тип свойства
     * @param property свойство
     * @return Class    тип свойства
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Class getValSetterMethodParamType(Class clazz, Property property)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field field = clazz.getDeclaredField(property.getName());
        Class c = field.getType();
        if (c.equals(long.class)) {
            return long.class;
        } else if (c.equals(int.class)) {
            return int.class;
        } else if (c.equals(short.class)) {
            return short.class;
        } else if (c.equals(byte.class)) {
            return byte.class;
        } else if (c.equals(double.class)) {
            return double.class;
        } else if (c.equals(float.class)) {
            return float.class;
        } else if (c.equals(boolean.class)) {
            return boolean.class;
        }
        throw new IllegalArgumentException("Property type is not primitive type");
    }

    /**
     * Преобразует строку значения поля некоторого примитивного типа в нужный тип
     * @param type              необходимое значение типа поля
     * @param propertyValue     строковое значение этого поля
     * @return объект , преобразованный в нужный тип данных
     */
    private Object getValSetterMethodParam(Class type, String propertyValue) {
        if (type.equals(long.class)) {
            return Long.parseLong(propertyValue);
        } else if (type.equals(int.class)) {
            return Integer.parseInt(propertyValue);
        } else if (type.equals(short.class)) {
            return Short.parseShort(propertyValue);
        } else if (type.equals(byte.class)) {
            return Byte.parseByte(propertyValue);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(propertyValue);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(propertyValue);
        } else if (type.equals(boolean.class)) {
            return Boolean.parseBoolean(propertyValue);
        }
        throw new IllegalArgumentException("Property type is not primitive type");
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws IllegalClassFormatException {
        Object obj = objById.get(id);
        if (obj != null) {
            return obj;
        }
        Bean bean = getBeanById(id);
        if (bean == null) {
            return null;
        }
        try {
            return makeInstance(bean);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws IllegalClassFormatException {
        Object obj = objByClassName.get(className);
        if (obj != null) {
            return obj;
        }
        Bean bean = getBeanByClassName(className);
        if (bean == null) {
            return null;
        }
        try {
            return makeInstance(bean);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
