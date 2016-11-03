package track.container;

import java.lang.reflect.Field;
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
    private List<Bean> beans;
    private Map<String, Object> objByClass;
    private Map<String, Object> objById;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
        objByClass = new HashMap<>();
        objById = new HashMap<>();
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        if (objById.containsKey(id)) {
            return objById.get(id);
        } else {
            for ( Bean b : beans) {
                if (b.getId().equals(id)) {
                    Object obj;
                    try {
                        obj = beanToObject(b);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return obj;
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
        if (objByClass.containsKey(className)) {
            return objByClass.get(className);
        } else {
            for ( Bean b : beans) {
                if (b.getClassName().equals(className)) {
                    Object obj;
                    try {
                        obj = beanToObject(b);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return obj;
                }
            }
            return null;
        }
    }

    public Object beanToObject(Bean bean) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvalidConfigurationException {
        Class clazz = Class.forName(bean.getClassName());
        Field[] fields = clazz.getDeclaredFields();
        Object instance = clazz.newInstance();

        for (Property property : bean.getProperties().values()) {
            for (Field field : fields) {
                if (property.getName().equals(field.getName())) {
                    if (property.getType() == ValueType.VAL) {
                        field.setAccessible(true);
                        field.set(instance, Integer.parseInt(property.getValue()));
                        field.setAccessible(false);
                        break;
                    } else {
                        String subName = refBean(property.getValue()).getClassName();
                        Class subClazz = Class.forName(subName);
                        Field[] subFields = subClazz.getDeclaredFields();
                        for (Field subField : subFields) {
                            if (subField.getName().equals(bean.getClassName())) {
                                throw new InvalidConfigurationException("Circular dependency");
                            }
                        }
                        field.setAccessible(true);
                        field.set(instance, getById(property.getValue()));
                        field.setAccessible(false);
                    }
                }
            }
        }
        objByClass.put(bean.getClassName(), instance);
        objById.put(bean.getId(), instance);
        return instance;
    }

    private Bean refBean(String id) throws InvalidConfigurationException {
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                return bean;
            }
        }

        throw new InvalidConfigurationException("Invalid class id");
    }
}
