package track.container;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sun.xml.internal.ws.model.AbstractWrapperBeanGenerator;
import org.xml.sax.SAXException;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */

public class Container {
    private List<Bean> beans;
    private Map<String, Bean> beanMap = new HashMap<>();
    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClass = new HashMap<>();

    // Реализуйте этот конструктор, используется в тестах!
    public Container(String pathToConfig) throws InvalidConfigurationException {
        BeanXmlReadel bxr = new BeanXmlReadel();
        try {
            beans = bxr.parseBeans(pathToConfig);
            beans.forEach(x -> beanMap.put(x.getId(), x));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public Container(List<Bean> beans) {
        this.beans = beans;
    }


     /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    private void generateObject(String beanId) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        beanMap.get(beanId).getProperties().forEach((key, value) -> {
            if (value.getType().equals(ValueType.REF) && !objByName.containsKey(value.getValue())) {
                try {
                    generateObject(value.getValue());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(beanMap.get(beanId).getClassName());

        Class currClass = Class.forName(beanMap.get(beanId).getClassName());
        Object obj = currClass.newInstance();

        beanMap.get(beanId).getProperties().forEach((name, property) -> {
            String methodName = "set" + property.getName().substring(0,1).toUpperCase() +
                    property.getName().substring(1);
            Class[] argsTypes = new Class[0];
            Object[] args = new Object[0];

            if (property.getType().equals(ValueType.VAL)) {
                argsTypes = new Class[] { int.class };
                args = new Object[] { (int) Integer.valueOf(property.getValue()) };

            } else {
                try {
                    
                    Bean childBean = beanMap.get(property.getValue());
                    argsTypes = new Class[] { Class.forName(childBean.getClassName()) };
                    args = new Object[] { objByName.get(childBean.getId()) };

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }


            Method setter = null;
            try {
                setter = currClass.getMethod(methodName, argsTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (setter != null) {
                try {
                    setter.invoke(obj, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        objByName.put(beanId, obj);
    }

    public Object getById(String id) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!objByName.containsKey(id)) {
            generateObject(id);
        }
        return objByName.get(id);
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        return null;
    }
}
