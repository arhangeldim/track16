package track.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClassName = new HashMap<>();
    private List<Bean> beans;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
//        for (Bean bean:beans) {
//            objByName.put(bean.getId(), bean);
//            objByClassName.put(bean.getClassName(), bean);
//        }
        this.beans = beans;
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws Exception {
        if (!objByName.containsKey(id)) {
            Bean bean = null;
            for (Bean curBean : beans) {
                if (curBean.getId().equals(id)) {
                    bean = curBean;
                    break;
                }
            }
            if (bean == null) {
                return null;
            }
            newObject(bean);
        }
        return objByName.get(id);
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws Exception {
        if (!objByClassName.containsKey(className)) {
            Bean bean = null;
            for (Bean curBean : beans) {
                if (curBean.getClassName().equals(className)) {
                    bean = curBean;
                    break;
                }
            }
            if (bean == null) {
                return null;
            }
            newObject(bean);
        }

        return objByClassName.get(className);
    }

    private void newObject(Bean bean) throws Exception {
        Class<?> clazz;
        clazz = Class.forName(bean.getClassName());
        Object object = clazz.newInstance();
        //to avoid cyclic references
        objByName.put(bean.getId(), object);
        objByClassName.put(bean.getClassName(), object);
        //
        for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue().getValue();
            ValueType type = entry.getValue().getType();
            Method method;
            if (type.equals(ValueType.REF)) {
                Object refObject = getById(value);
                Class refClazz = clazz.getDeclaredField(name).getType();
                method = clazz.getMethod(getSetterName(name), refClazz);
                method.invoke(object, refObject);
            } else {
                Class fieldClazz = clazz.getDeclaredField(name).getType();
                String typeName = fieldClazz.getTypeName();
                method = clazz.getMethod(getSetterName(name), fieldClazz);
                Object param;
                switch (typeName.toLowerCase()) {
                    case "boolean":
                        param = Boolean.valueOf(value);
                        break;
                    case "byte":
                        param = new Byte(value);
                        break;
                    case "short":
                        param = new Short(value);
                        break;
                    case "char":
                        param = value.charAt(0);
                        break;
                    case "int":
                        param = new Integer(value);
                        break;
                    case "float":
                        param = new Float(value);
                        break;
                    case "long":
                        param = new Long(value);
                        break;
                    case "double":
                        param = new Double(value);
                        break;
                    default:
                        param = null;
                        break;
                }
                method.invoke(object, param);
            }
        }
    }
    
    private String getSetterName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
