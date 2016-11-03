package track.container;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

/**
 * Keeps track of objects in Beans list, constructs and returns objects of given classes
 * of given id's in Beans list.
 * 
 * Module uses lazy computing strategy: objects are constructed only when it's necessary.
 *
 * @author Sergey Ivanychev
 */
public class Container {

    /**
     * Contains list, that was given during initialization.
     */
    private ArrayList<Bean> beans;
    /**
     * Associates Bean object with its id
     */
    private HashMap<String, Bean> idDict;
    /**
     * Associates Class signature with Bean objects with such class
     */
    private HashMap<String, HashSet<Bean>> classDict;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        beans = new ArrayList<>(beans);
        idDict = new HashMap<>();
        classDict = new HashMap<>();
        beans.forEach(bean -> idDict.put(bean.getId(), bean));
        beans.forEach(bean -> {
            String className = bean.getClassName();
            if (classDict.containsKey(className)) {
                classDict.get(className).add(bean);
            } else {
                classDict.put(className, new HashSet<>());
                classDict.get(className).add(bean);
            }
        });
    }

    /**
     * Gets Class of given field in clazz
     * 
     * @param clazz we are searching for type in it
     * @param fieldName name of some field in Class definition of obj
     * @return Class, which is type of fieldName field in obj Class
     * @throws Exception if there's no such field
     */
    private Class getFieldType(Class clazz, String fieldName) throws Exception {
        Class fieldType;
        try {
            fieldType = clazz.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new Exception("No such field: " + fieldName);
        }
        return fieldType;
    }

    /**
     * Given object obj and its property, getSetter extracts setter of the field.
     * It supposes that the method is called according to Google checkstyle rules.
     *
     *      fieldName -> getFieldName
     *
     * @param clazz we are searching setter in clazz
     * @param property Property object
     * @return setter method of input property
     * @throws NoSuchMethodException if setter not found
     * @throws Exception if failed to get field type
     */
    private Method getSetter(Class clazz, Property property) throws NoSuchMethodException, Exception {
        String propertyName = property.getName();
        Class fieldType = getFieldType(clazz, propertyName);
        String name = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method setter = clazz.getMethod(name, fieldType);
        return setter;
    }

    /**
     * Converts input string to a Java primitive object.
     * @param asString string representation of converting value
     * @param fieldType type of field, that will contain converted value
     * @return Object of type fieldType with value contained in asString
     */
    private Object getPrimitiveFromString(String asString, Class fieldType) {
        return PrimitiveConverter.convertToPrimitive(asString, fieldType);
    }

    /**
     * According to property type (reference or value)), it returns its value as an object.
     * @param clazz needed to extract field types
     * @param property processing Property class
     * @return object of received value
     * @throws Exception if couldn't get field type
     */
    private Object getValueFromProperty(Class clazz, Property property) throws Exception {
        if (property.getType() == ValueType.VAL) {
            String valAsString = property.getValue();
            Object val = getPrimitiveFromString(valAsString, getFieldType(clazz, property.getName()));
            return val;
        } else {
            return getById(property.getValue());
        }
    }

    /**
     * Updates obj with input property
     * @param obj update object
     * @param property new property
     */
    private void applyProperty(Object obj, Property property) {
        Method setter;
        try {
            setter = getSetter(obj.getClass(), property);
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't get setter: " + e.getMessage());
        }
        try {
            Object value = getValueFromProperty(obj.getClass(), property);
            setter.invoke(obj, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't call setter: " + e.toString());
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws IllegalArgumentException {
        if (!idDict.containsKey(id)) {
            throw new IllegalArgumentException("No such key");
        }
        Bean bean = idDict.get(id);
        String className = bean.getClassName();
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class not found exception: +" + e.getMessage());
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't instance new object: " + e.getMessage());
        }
        Map<String, Property> propertyMap = bean.getProperties();
        for (String key: propertyMap.keySet()) {
            applyProperty(obj, propertyMap.get(key));
        }

        return obj;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws IllegalArgumentException {
        HashSet<Bean> beans  = classDict.get(className);
        for (Bean bean: beans) {
            return getById(bean.getId());
        }
        throw new IllegalArgumentException("No objects of such class");
    }
}

class PrimitiveConverter {
    public static Object convertToPrimitive(String asString, Class fieldType) {
        Object out;
        switch (fieldType.getName().toLowerCase()) {
            case "int":
                out = new Integer(asString);
                break;
            case "long":
                out = new Long(asString);
                break;
            case "short":
                out = new Short(asString);
                break;
            case "byte":
                out = new Byte(asString);
                break;
            case "char":
                out = asString.charAt(0);
                break;
            case "boolean":
                out = Boolean.valueOf(asString);
                break;
            case "float":
                out = new Float(asString);
                break;
            case "double":
                out = new Double(asString);
                break;
            default:
                out = null;
                break;
        }
        return out;
    }
}
