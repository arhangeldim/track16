package track.container;

import track.container.config.Property;

import java.lang.reflect.Field;

/**
 * Created by altair on 04.11.16.
 */
public class PropertyHelper {

    public static String getSetterMethodName(String propertyName) {
        return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * Нужен для определения типа параметра для set[PropertyName] метода.
     *
     * @param clazz    класс, для которого ищется тип свойства
     * @param property свойство
     * @return Class    тип свойства
     * @throws NoSuchFieldException     Если поле не было найдено
     * @throws IllegalAccessException   Нет доступа к полю класса
     */
    public static Class getValSetterMethodParamType(Class clazz, Property property)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(property.getName());
        Class type = field.getType();
        if (type.equals(long.class)) {
            return long.class;
        } else if (type.equals(int.class)) {
            return int.class;
        } else if (type.equals(short.class)) {
            return short.class;
        } else if (type.equals(byte.class)) {
            return byte.class;
        } else if (type.equals(double.class)) {
            return double.class;
        } else if (type.equals(float.class)) {
            return float.class;
        } else if (type.equals(boolean.class)) {
            return boolean.class;
        }
        throw new IllegalArgumentException("Property type is not primitive type");
    }

    /**
     * Преобразует строку значения поля некоторого примитивного типа в нужный тип
     * @param type              необходимое значение типа поля
     * @param propertyValue     строковое значение этого поля
     * @return объект , преобразованный в нужный тип данных
     */
    public static Object getValSetterMethodParam(Class type, String propertyValue) {
        if (type.equals(long.class)) {
            return Long.parseLong(propertyValue);
        } else if (type.equals(int.class)) {
            return Integer.parseInt(propertyValue);
        } else if (type.equals(short.class)) {
            return Short.parseShort(propertyValue);
        } else if (type.equals(byte.class)) {
            return Byte.parseByte(propertyValue);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(propertyValue);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(propertyValue);
        } else if (type.equals(boolean.class)) {
            return Boolean.parseBoolean(propertyValue);
        }
        throw new IllegalArgumentException("Property type is not primitive type");
    }
}
