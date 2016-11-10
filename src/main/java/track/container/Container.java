package track.container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

    private static final String INT = "int";

    private Map<String,Bean> beansById;
    private Map<String,Bean> beansByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        beansById = new HashMap<>();
        beansByClassName = new HashMap<>();

        for (Bean bean: beans) {
            this.beansById.put(bean.getId(),bean);
            this.beansByClassName.put(bean.getClassName(),bean);
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        return makeRequiredObject(beansById.get(id));
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        return makeRequiredObject(beansByClassName.get(className));
    }

    public Object makeRequiredObject(Bean bean) {
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
                char[] properyNameCharSet = property.getName().toCharArray();
                properyNameCharSet[0] = Character.toUpperCase(properyNameCharSet[0]);
                String properyName = new String(properyNameCharSet);
                for (Method method: object.getClass().getDeclaredMethods()) {
                    if (method.getName().equals("set" + properyName)) {
                        if (method.getParameterTypes()[0].getName().equals(INT)) {
                            propertyValue = Integer.parseInt((String) propertyValue);
                        }
                        method.invoke(object,propertyValue);
                    }
                }
            }
            return object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
