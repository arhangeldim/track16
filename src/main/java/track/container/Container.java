package track.container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private List<Bean> beans;
    private Map<String, Bean> beanById;
    private Map<String, Bean> beanByClassName;
    private Map<String, Object> objByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
        beanById = new HashMap<>();
        beanByClassName = new HashMap<>();
        objByClassName = new HashMap<>();
        for (Bean bean: beans) {
            beanById.put(bean.getId(), bean);
            beanByClassName.put(bean.getClassName(), bean);
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            InvalidConfigurationException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        if (beanById.containsKey(id)) {
            Bean bean = beanById.get(id);
            if (objByClassName.containsKey(bean.getClassName())) {
                return objByClassName.get(bean.getClassName());
            }
            Class clazz = Class.forName(bean.getClassName());
            Object object = clazz.newInstance();
            for (Map.Entry<String, Property> propertyPair: bean.getProperties().entrySet()) {
                Property property = propertyPair.getValue();
                Field field = clazz.getDeclaredField(property.getName());
                field.setAccessible(true);
                Object value;
                if (property.getType() == ValueType.VAL) {
                    value = stringToPrimitive(field.getType(), property.getValue());
                } else {
                    value = getByClass(field.getType().getName());
                }
                field.set(object, value);
                field.setAccessible(false);
            }
            objByClassName.put(bean.getClassName(), object);
            return object;
        }
        throw new InvalidConfigurationException("Invalid configuration");
    }


    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, InvalidConfigurationException, InvocationTargetException, NoSuchFieldException,
            NoSuchMethodException {
        if (beanByClassName.containsKey(className)) {
            return getById(beanByClassName.get(className).getId());
        }
        throw new InvalidConfigurationException("Invalid configuration");
    }

    private Object stringToPrimitive(Type type, String value) {
        switch (type.getTypeName()) {
            case ("byte") : return Byte.parseByte(value);
            case ("short") : return Short.parseShort(value);
            case("int") : return Integer.parseInt(value);
            case ("long") : return Long.parseLong(value);
            case ("boolean") : return Boolean.parseBoolean(value);
            case ("float") : return Float.parseFloat(value);
            case ("double") : return Double.parseDouble(value);
            case ("char") : return value.charAt(0);
            default: return null;
        }
    }
}

