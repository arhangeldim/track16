package track.container;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private List<Bean> beans;
    private HashMap<String, Object> objByName = new HashMap<String, Object>();
    private HashMap<String, Object> objByClassName = new HashMap<String, Object>();

    public Container(String path) throws InvalidConfigurationException, IOException {
        File config = new File(path);
        if (config.exists()) {
            JsonConfigReader jsonConfigReader = new JsonConfigReader();
            beans = jsonConfigReader.parseBeans(config);
        } else {
            throw new IOException("There's no such File");
        }
    }

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
    }

    public Object getById(String id) throws ClassNotFoundException, ConfigurationException {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        }

        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                return createObject(bean);
            }
        }
        return null;
    }

    private Object createObject(Bean<HashMap<String, Property>> bean) throws ClassNotFoundException, ConfigurationException {

        Class myNewBean = null;
        try {
            myNewBean = Class.forName(bean.getClassName());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("We have't got a such class");
        }
        Object object = null;
        try {
            object = myNewBean.newInstance(); // We will not Get Here Because all methods are public
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        objByClassName.put(bean.getId() + " is Creating now, abort!!!", null);
        HashMap<String, Property> propertyHashMap = bean.getProperties();

        Method[] methods = myNewBean.getMethods();
        for (String key : propertyHashMap.keySet()) {
            Property property = propertyHashMap.get(key);
            String methodName = "set" + property.getName().toString().substring(0, 1).toUpperCase();
            methodName += property.getName().toString().substring(1);
            if (objByClassName.containsKey(property.getName() + " is Creating now, abort!!!")) {
                return null; // ЦИКЛ!!!!!!
            }
            for (Method method : methods) {
                if (method.getName().toString().equals(methodName)) {
                    int value = Integer.parseInt(property.getValue().toString());
                    try {
                        if (property.getType() == ValueType.REF) {
                            Object newPropClass1 = getById(property.getValue().toString());
                            method.invoke(object, newPropClass1);
                        } else {
                            method.invoke(object, value);
                        }
                    } catch (Exception e) {
                        throw new ConfigurationException("There is no such method");
                    }
                }
            }
        }

        objByClassName.remove(bean.getId() + " is Creating now, abort!!!");
        objByClassName.put(bean.getClassName(), object);
        objByName.put(bean.getId(), object);
        return object;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ClassNotFoundException, ConfigurationException {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        }

        for (Bean bean : beans) {
            if (bean.getClassName().equals(className)) {
                return createObject(bean);
            }
        }

        return null;
    }
}
