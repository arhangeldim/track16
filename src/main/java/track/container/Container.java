package track.container;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Class;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {
    private Map<String, Object> objByName;      // <id, object[Car, Engine, Gear]>; id = name
    private Map<String, Object> objByClassName; // <className, object[Car, Engine, Gear]>
    private Map<String, Bean> beansById;        // <id, Bean object>
    private Map<String, Bean> beansByClass;     // <className, Bean object>
    private Map<String, Boolean> isUpdated;     // <className, if was updated by bean properties>

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        objByName = new HashMap<>();
        objByClassName = new HashMap<>();
        beansByClass = new HashMap<>();
        beansById = new HashMap<>();
        isUpdated = new HashMap<>();
        for (Bean bean : beans) {
            this.beansById.put(bean.getId(), bean);
            this.beansByClass.put(bean.getClassName(), bean);
        }
    }

    private Object getObject(Bean bean) throws InvalidConfigurationException {
        try {
            if (bean == null) {
                throw new InvalidConfigurationException("Bean object is null");
            }
            Class clazz = Class.forName(bean.getClassName());
            String className = bean.getClassName();
            Object obj = objByClassName.get(className);
            if (obj == null) {
                obj = clazz.newInstance();
                String name = bean.getId();

                objByName.put(name, obj);
                objByClassName.put(bean.getClassName(), obj);

                isUpdated.put(className, Boolean.FALSE);
            }
            if (isUpdated.get(className) || bean.getProperties().isEmpty()) {
                return obj;
            }

            for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
                Property prop = entry.getValue();
                Field field = clazz.getDeclaredField(prop.getName());
                field.setAccessible(true);
                switch (prop.getType()) {
                    case VAL:
                        int value = Integer.parseInt(prop.getValue()); // !!! ONLY FOR INTEGER VALUES
                        field.set(obj, value);
                        break;
                    case REF:
                        System.out.println(field.getType().getName());
                        Bean refBean = beansByClass.get(field.getType().getName());
                        if (refBean != null) {
                            field.set(obj, getObject(refBean));
                        } else {
                            Map<String, Property> refProps = new HashMap<>();
                            Bean emptyBean = new Bean(prop.getValue(), field.getType().getName(), refProps);
                            field.set(obj, getObject(emptyBean));
                        }
                        break;
                    default:
                }
            }
            isUpdated.put(className, Boolean.TRUE);
            return obj;
        } catch (IllegalAccessException |
                 InstantiationException |
                 ClassNotFoundException |
                 NoSuchFieldException |
                 NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        try {
            return getObject(beansById.get(id));
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        try {
            return getObject(beansByClass.get(className));
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Container {");
        for (Map.Entry<String, Object> entry : objByName.entrySet()) {
            res.append("\n");
            res.append(entry.getValue().toString());
        }
        res.append("\n}");
        return res.toString();
    }
}
