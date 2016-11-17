package track.container;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
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
        try {
            return makeRequiredObject(beansById.get(id));
        }
        catch (InvalidConfigurationException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        try {
            return makeRequiredObject(beansByClassName.get(className));
        }
        catch (InvalidConfigurationException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Object makeRequiredObject(Bean bean) throws InvalidConfigurationException {
        try {
            Class beanClass = Class.forName(bean.getClassName());
            Object object = beanClass.getConstructor().newInstance();
            for (Property property : bean.getProperties()) {
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
                for (Method method : object.getClass().getDeclaredMethods()) {
                    if (method.getName().equals("set" + properyName)) {
                        for (Primitives primitive: Primitives.values()){
                            if (method.getParameterTypes()[0].getName().equals(primitive.getType())){
                                propertyValue = primitive.parse((String) propertyValue);
                            }
                        }
                        method.invoke(object, propertyValue);
                    }
                }
            }
            return object;
        }
        catch (Exception e){
            InvalidConfigurationException ex = new InvalidConfigurationException("Invalid config");
            throw ex;
        }
    }


    private enum Primitives{
        BOOLEAN{
            private String type = "boolean";
            public Boolean parse(String value){
                return Boolean.parseBoolean(value);
            }

            @Override
            public String getType() {
                return type;
            }
        },
        SHORT{
            private String type = "short";
            public Short parse(String value){
                return Short.parseShort(value);
            }

            @Override
            public String getType() {
                return type;
            }
        },
        INTEGER{
            private String type = "int";
            public Integer parse(String value){
                return Integer.parseInt(value);
            }

            @Override
            public String getType() {
                return type;
            }
        },
        LONG{
            private String type = "long";
            public Long parse(String value){
                return Long.parseLong(value);
            }

            @Override
            public String getType() {
                return type;
            }

        },
        DOUBLE{
            private String type = "double";
            public Double parse(String value){
                return Double.parseDouble(value);
            }

            @Override
            public String getType() {
                return type;
            }
        },
        FLOAT{
            private String type = "float";
            public Float parse(String value){
                return Float.parseFloat(value);
            }

            @Override
            public String getType() {
                return type;
            }
        };

        public abstract Object parse(String value);
        public abstract String getType();

    }

}
