package track.container;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.ValueType;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */

public class Container {
    private List<Bean> beans;
    private Map<String, Bean> beanMap = new HashMap<>();
    private Map<String, Bean> beanClassMap = new HashMap<>();
    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClass = new HashMap<>();

    // Реализуйте этот конструктор, используется в тестах!
    public Container(String pathToConfig) throws InvalidConfigurationException {
        BeanXmlReader bxr = new BeanXmlReader();
        try {
            beans = bxr.parseBeans(pathToConfig);
            beans.forEach(x -> beanMap.put(x.getId(), x));
            beans.forEach(x -> beanClassMap.put(x.getClassName(), x));
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

     /*
     Будем создавать объект из Bean следующим обдразом:
     Сначала смотрим, не будет ли ссылаться наш будущий объект на другие, которые мы ещё не создали.
     Если такие есть, то рекурсивно создаём их.

     Затем создаём объект, и запускаем его сеттеры для инициализации всех параметров.
      */
    private void generateObject(String beanId) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {

        //рекурсивное создание потомков
        beanMap.get(beanId).getProperties().forEach((key, value) -> {
            if (value.getType().equals(ValueType.REF) && !objByName.containsKey(value.getValue())) {
                try {
                    generateObject(value.getValue());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        //System.out.println(beanMap.get(beanId).getClassName());


        //создание объекта
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

        objByClass.put(beanMap.get(beanId).getClassName(), obj);
        objByName.put(beanId, obj);
    }

    public Object getById(String id) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!objByName.containsKey(id)) {
            generateObject(id);
            //System.out.println(objByClass.toString());
        }
        return objByName.get(id);
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        if (!objByClass.containsKey(className)) {
            generateObject(beanClassMap.get(className).getId());
        }
        //System.out.println(objByClass.toString());
        return objByClass.get(className);
    }
}
