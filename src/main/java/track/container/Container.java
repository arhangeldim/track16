package track.container;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private Map<String, Bean> beanById;
    private Map<String, Bean> beanByClassName;
    private Map<String, Object> objByName;
    private Map<String, Object> objByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        beanById = new HashMap<>();
        beanByClassName = new HashMap<>();
        objByName = new HashMap<>();
        objByClassName = new HashMap<>();
        for (Bean bean : beans) {
            beanById.put(bean.getId(), bean);
            beanByClassName.put(bean.getClassName(), bean);
        }
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws ClassNotFoundException, InvalidConfigurationException, IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        } else {
            Bean bean = beanById.get(id);
            if (bean == null) {
                throw new InvalidConfigurationException("There are not bean with this id");
            }
            Class clazz = Class.forName(bean.getClassName());
            Object object = clazz.newInstance();
            for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
                Property property = entry.getValue();
                Field field = clazz.getDeclaredField(property.getName());
                field.setAccessible(true);
                if (property.getType().equals(ValueType.VAL)) {
                    Object value = Integer.parseInt(property.getValue());
                    field.set(object, value);
                } else if (property.getType().equals(ValueType.REF)) {
                    String depClassName = beanById.get(property.getValue()).getClassName();
                    if (!objByClassName.containsKey(depClassName)) {
                        Class depClazz = Class.forName(depClassName);
                        if (isCircularDependency(bean.getClassName(), depClazz.getDeclaredFields())) {
                            throw new InvalidConfigurationException("There is a circular dependency");
                        } else {
                            Object value = getByClass(depClassName);
                            field.set(object, value);
                        }
                    } else {
                        field.set(object, objByClassName.get(depClassName));
                    }
                }
            }
            objByClassName.put(bean.getClassName(), object);
            objByName.put(bean.getId(), object);
            return object;
        }
    }

    private boolean isCircularDependency(String className, Field[] declaredFields) {
        for (Field field : declaredFields) {
            if (field.getType().getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ClassNotFoundException, NoSuchFieldException,
            InstantiationException, InvalidConfigurationException, IllegalAccessException {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        } else {
            return getById(beanByClassName.get(className).getId());
        }
    }
}
