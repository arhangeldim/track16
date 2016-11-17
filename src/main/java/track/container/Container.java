package track.container;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                return createObject(bean);
            }
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        for (Bean bean : beans) {
            if (bean.getClassName().equals(className)) {
                return createObject(bean);
            }
        }
        return null;
    }

    private Object createObject(Bean bean) {
        try {
            Class classOfBean = Class.forName(bean.getClassName());
            Object createdObject = classOfBean.getConstructor().newInstance();
            for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
                for (Field field : createdObject.getClass().getDeclaredFields()) {
                    if (!field.getName().equals(entry.getKey())) {
                        continue;
                    }
                    if (entry.getValue().getType() == ValueType.VAL) {
                        String value = entry.getValue().getValue();
                        setValForField(value, field, createdObject);
                    } else {
                        Object referense = getById(entry.getValue().getValue());
                        field.setAccessible(true);
                        field.set(createdObject, referense);
                        field.setAccessible(false);
                    }
                }
            }

            return createdObject;
        } catch (ClassNotFoundException | InvalidPrimitiveTypeException | IllegalAccessException |
                NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void setValForField(String value, Field field, Object createdObject) throws InvalidPrimitiveTypeException,
            IllegalAccessException {
        field.setAccessible(true);
        switch (field.getType().getTypeName().toLowerCase()) {
            case "boolean":
                field.set(createdObject, Boolean.parseBoolean(value));
                break;
            case "byte":
                field.set(createdObject, Byte.parseByte(value));
                break;
            case "short":
                field.set(createdObject, Short.parseShort(value));
                break;
            case "int":
                field.set(createdObject, Integer.parseInt(value));
                break;
            case "char":
                field.set(createdObject, value.charAt(0));
                break;
            case "long":
                field.set(createdObject, Long.parseLong(value));
                break;
            case "float":
                field.set(createdObject, Float.parseFloat(value));
                break;
            case "double":
                field.set(createdObject, Double.parseDouble(value));
                break;
            default:
                throw new InvalidPrimitiveTypeException("No such primitive type " + field.getType().getTypeName().toLowerCase());
        }
        field.setAccessible(false);
    }

}

