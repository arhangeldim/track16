package track.container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private List<Bean> beans;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {

        for (Bean bean: beans) {
            if (bean.getId().equals(id)) {
                return makeRequeredObject(bean);
            }
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        for (Bean bean: beans) {
            if (bean.getClassName().equals(className)) {
                return makeRequeredObject(bean);
            }
        }
        return null;
    }

    public Object makeRequeredObject(Bean bean) {
        try {
            Class beanClass = Class.forName(bean.getClassName());
            Object object = beanClass.getConstructor().newInstance();
            for (Property property: bean.getProperties()) {
                Object propertyValue = null;
                if (property.getType().equals(ValueType.VAL)) {
                    propertyValue = property.getVal();
                }
                if (property.getType().equals(ValueType.REF)) {
                    propertyValue = getById(property.getVal());
                }
                for (Field field: object.getClass().getDeclaredFields()) {
                    if (field.getName().equals(property.getName())) {
                        if (field.getType().getName().equals("int")) {
                            propertyValue = Integer.parseInt((String) propertyValue);
                        }
                        field.setAccessible(true);
                        field.set(object,propertyValue);
                    }
                }
            }

            return object;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
