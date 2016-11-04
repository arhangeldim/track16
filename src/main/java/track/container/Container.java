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
            if (bean.getId().equals(id)) {
                return bean;
            }
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
     * @param bean      Bean для которого создается соответсвие
     * @param object    Объект, собранный по этому bean'у
     */
    public void saveObject(Bean bean, Object object) {
        objByClassName.put(bean.getClassName(), object);
        objById.put(bean.getId(), object);
    }

    /**
     * Обертка над makeInstanceUnwrapped. Перехватывает часть исключений и оборочанивает другими.
     * @param bean      Бин, по которому создается объект
     * @return Object созданный по bean'у
     * @throws InvocationTargetException       Возникает , если в set[PropertyName] возникает какая-то ошибка
     * @throws IllegalClassFormatException     Возникает , если в сигнатуре класса допущены ошибки
     */
    private Object makeInstance(Bean bean)
            throws InvocationTargetException,
            IllegalClassFormatException {
        try {
            return makeInstanceUnwrapped(bean);
        } catch (ClassNotFoundException ex) {
            System.out.println("Class " + bean.getClassName() + " not found");
        } catch (InstantiationException ex) {
            IllegalClassFormatException exception = new IllegalClassFormatException("Class " +
                    bean.getClassName() + " must have default constructor");
            exception.addSuppressed(ex);
            throw exception;
        } catch (IllegalAccessException ex) {
            IllegalClassFormatException exception =
                    new IllegalClassFormatException("Class has IllegalAccessException " +
                            bean.getClassName());
            exception.addSuppressed(ex);
            throw exception;
        }
        return null;
    }

    /**
     * @param bean      Bean по которому собирается объект
     * @return объект , собранный по bean'у
     * @throws ClassNotFoundException       Ошибка, если класс не найден
     * @throws IllegalAccessException       Если невозможен доступ к контруктору
     * @throws InstantiationException       Невозможно создать объект
     * @throws InvocationTargetException    Ошибка вызова метода set[PropertyName]
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
                    Method method = clazz.getMethod(getSetterMethodName(propEntry.getKey()), type);
                    method.invoke(obj, getValSetterMethodParam(type, property.getValue()));
                } else {
                    Object paramObj = getById(property.getValue());
                    Class type = paramObj.getClass();
                    Method method = clazz.getMethod(getSetterMethodName(propEntry.getKey()), type);
                    method.invoke(obj, paramObj);
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
     * Нужен для определения типа параметра для set[PropertyName] метода.
     *
     * @param clazz    класс, для которого ищется тип свойства
     * @param property свойство
     * @return Class    тип свойства
     * @throws NoSuchFieldException     Если поле не было найдено
     * @throws IllegalAccessException   Нет доступа к полю класса
     */
    private Class getValSetterMethodParamType(Class clazz, Property property)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(property.getName());
        Class type = field.getType();
        if (type.equals(long.class)) {
            return long.class;
        } else if (type.equals(int.class)) {
            return int.class;
        } else if (type.equals(short.class)) {
            return short.class;
        } else if (type.equals(byte.class)) {
            return byte.class;
        } else if (type.equals(double.class)) {
            return double.class;
        } else if (type.equals(float.class)) {
            return float.class;
        } else if (type.equals(boolean.class)) {
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
