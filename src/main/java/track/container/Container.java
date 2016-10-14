package track.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
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
    private Map<String, Bean> beanById;
    private Map<String, Bean> beanByClass;
    private Map<String, Object> objById;
    private Map<String, Object> objByClass;

    public Container(List<Bean> beans) {
        beanById = new LinkedHashMap<>();
        beanByClass = new LinkedHashMap<>();
        objById = new LinkedHashMap<>();
        objByClass = new LinkedHashMap<>();

        for (Bean bean : beans) {
            beanById.put(bean.getId(), bean);
            beanByClass.put(bean.getClassName(), bean);
        }
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) {
        Object object = objById.get(id);

        if (object == null) {
            Bean bean = beanById.get(id);
            if (bean != null) {
                object = createObjectFromBean(bean);
            }
        }

        return object;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) {
        Object object = objByClass.get(className);

        if (object == null) {
            Bean bean = beanByClass.get(className);
            if (bean != null) {
                object = createObjectFromBean(bean);
            }
        }

        return object;
    }

    /**
     *  Создать объект на основе бина
     */
    private Object createObjectFromBean(Bean bean) {
        Object object = null;

        try {
            /* Найти класс, описанный в бине, и создать объект этого класса */
            Class clazz = Class.forName(bean.getClassName());
            object = clazz.newInstance();

            /* Задать маппинги для созданного объекта */
            objById.put(bean.getId(), object);
            objByClass.put(bean.getClassName(), object);

            /* Десериализовать поля (properties) объекта */
            for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
                Property property = entry.getValue();

                if (property.getType() == ValueType.VAL) {
                    /* Если тип поля — значение, то найдём для него сеттер и запишем его в объект через сеттер */
                    try {
                        /* Пока обрабатываем только int */
                        Method setter = clazz.getMethod("set" + capitalize(property.getName()), int.class);
                        setter.invoke(object, Integer.parseInt(property.getValue()));
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        System.out.println(e.toString());
                    }
                } else if (property.getType() == ValueType.REF) {
                    /* Если тип поля — ссылка, то найдём объект, на который ведёт ссылка */
                    String refId = property.getValue();
                    Object ref = objById.get(refId);

                    /* Если объект не найден, то он ещё не создан. Попробуем его создать */
                    if (ref == null) {
                        Bean refBean = beanById.get(refId);

                        if (refBean != null) {
                            ref = createObjectFromBean(beanById.get(refId));
                        } else {
                            /* Если для данной ссылки не описан бин в конфиге, то мы ничего сделать не можем */
                            System.out.println(bean.getId() + " refers to a nonexistent bean " + refId);
                            continue;
                        }
                    }

                    /* Найдём для поля сеттер и запишем через него ссылку */
                    try {
                        Method setter = clazz.getMethod("set" + capitalize(property.getName()),
                                ref.getClass());
                        setter.invoke(object, ref);
                    } catch (NoSuchMethodException | InvocationTargetException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.toString());
        }

        return object;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
