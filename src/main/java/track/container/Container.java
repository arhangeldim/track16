package track.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    Map<String, Object> objByName;
    Map<String, Object> objByClassName;
    Map<String, Bean> beanByName;
    Map<String, Bean> beanByClassName;
    Stack<String> beanStack;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        objByName = new HashMap<>();
        objByClassName = new HashMap<>();
        beanByName = new HashMap<>();
        beanByClassName = new HashMap<>();
        beanStack = new Stack<String>();
        for (Bean bean : beans) {
            beanByName.put(bean.getId(), bean);
            beanByClassName.put(bean.getClassName(), bean);
        }
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvalidConfigurationException {
        if (objByName.keySet().contains(id)) {
            return objByName.get(id);
        } else {
            for (Map.Entry<String, Property> entry : beanByName.get(id).getProperties().entrySet()) {
                if (entry.getValue().getType() == ValueType.REF && !beanStack.contains(entry.getValue().getValue())) {
                    getById(entry.getValue().getValue());
                } else if (beanStack.contains(entry.getValue().getValue())) {
                    throw new InvalidConfigurationException("Loop in dependency graph");
                }

            }
            Object toReturn = fromId(id, beanByName);
            objByName.put(id, toReturn);
            objByClassName.put(beanByName.get(id).getClassName(), toReturn);
            return toReturn;

        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws InvocationTargetException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, ClassNotFoundException, InvalidConfigurationException {
        if (objByClassName.keySet().contains(className)) {
            return objByClassName.get(className);
        } else {
            for (Map.Entry<String, Property> entry : beanByClassName.get(className).getProperties().entrySet()) {
                if (entry.getValue().getType() == ValueType.REF && !beanStack.contains(entry.getValue().getValue())) {
                    beanStack.push(entry.getValue().getValue());
                    getById(entry.getValue().getValue());
                    beanStack.pop();
                } else {
                    throw new InvalidConfigurationException("Loop in dependency graph");
                }
            }
            Object toReturn = fromId(className, beanByClassName);
            objByName.put(beanByClassName.get(className).getId(), toReturn);
            objByClassName.put(beanByClassName.get(className).getClassName(), toReturn);
            return toReturn;
        }
    }

    private Object fromId(String id, Map<String, Bean> beanMap) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class objectClass = Class.forName(beanMap.get(id).getClassName());
        Constructor objectConstructor = objectClass.getConstructor();
        Object toReturn = objectConstructor.newInstance();
        Method[] methods = objectClass.getMethods();
        for (Method method : methods) {
            if (isSetter(method)) {
                for (Map.Entry<String, Property> entry : beanMap.get(id).getProperties().entrySet()) {
                    if (entry.getValue().getType() == ValueType.VAL &&
                            method.getName().toLowerCase().equals("set".concat(entry.getValue().getName()))) {
                        method.invoke(toReturn, Converter.convert(method.getParameterTypes()[0].toString(),
                                entry.getValue().getValue()));
                    } else if (entry.getValue().getType() == ValueType.REF &&
                            method.getName().toLowerCase().equals("set".concat(entry.getValue().getName()))) {
                        if (objByName.keySet().contains(entry.getValue().getValue())) {
                            method.invoke(toReturn, objByName.get(entry.getValue().getValue()));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterTypes().length == 0 &&
                !void.class.equals(method.getReturnType());
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set") && method.getParameterTypes().length == 1;
    }

}
