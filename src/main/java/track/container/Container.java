package track.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.ValueType;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Object> objByClassName;
    private List<Bean> beans;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) throws InvalidConfigurationException {
        objByClassName = new HashMap<>();
        this.beans = beans;
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws InvalidConfigurationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //Если нужный класс ещё не создан
        System.out.println("In getById");
        if (!objByClassName.containsKey(id)) {
            Bean bean = findBean(id);

            if (bean == null) {
                throw new InvalidConfigurationException("invalid id");
            }

            //Загрузка класса по имени
            Class clazz = Class.forName(bean.getClassName());
            //Поля класса
            Field[] fields = clazz.getDeclaredFields();
            Object object = clazz.newInstance();

            for (String property : bean.getProperties().keySet()) {
                for (Field field : fields) {
                    if (property.equals(field.getName())) {
                        if (bean.getProperties().get(property).getType() == ValueType.VAL) {
                            field.setAccessible(true);
                            field.set(object, Integer.parseInt(bean.getProperties().get(field.getName()).getValue()));
                            field.setAccessible(false);
                            break;
                        } else {
                            String subName = findBean(bean.getProperties().get(property).getValue()).getClassName();
                            Class subClazz = Class.forName(subName);
                            Field[] subFields = subClazz.getDeclaredFields();
                            for (Field subField : subFields) {
                                if (subField.getName().equals(bean.getClassName())) {
                                    throw new InvalidConfigurationException("Circular dependency");
                                }
                            }
                            field.setAccessible(true);
                            field.set(object, getById(bean.getProperties().get(property).getValue()));
                            field.setAccessible(false);
                        }
                    }
                }
            }
            objByClassName.put(id, object);
        }

        return objByClassName.get(id);
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws InvalidConfigurationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Bean bean : beans) {
            if (bean.getClassName().equals(className)) {
                return getById(bean.getId());
            }
        }
        throw new InvalidConfigurationException("Invalid class name");
    }

    private Bean findBean(String id) throws InvalidConfigurationException {
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                return bean;
            }
        }

        throw new InvalidConfigurationException("Invalid class id");
    }
}
