package track.container;

import java.lang.reflect.Field;
import java.util.*;

import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

public class Container {

    private Map<String, Bean> beanId;
    private Map<String, Bean> beanClass;
    private Map<String, Object> objByName;
    private Map<String, Object> objByClassName;

    public Container(List<Bean> beans) {
        beanId = new HashMap<>();
        beanClass = new HashMap<>();
        objByClassName = new HashMap<>();
        objByName = new HashMap<>();
        for (Bean bean : beans) {
            beanId.put(bean.getId(), bean);
            beanClass.put(bean.getClassName(), bean);
        }
    }

    public Object getById(String id) throws IllegalAccessException, InstantiationException, NoSuchFieldException, InvalidConfigurationException, ClassNotFoundException {
        if (objByName.containsKey(id)) {
            return objByName.get(id);
        } else {
            if (beanId.get(id) == null) {
                throw new InvalidConfigurationException("No id");
            }
            Class clazz = Class.forName(beanId.get(id).getClassName());
            Object obj = clazz.newInstance();
            for (Map.Entry<String, Property> entry : beanClass.get(id).getProperties().entrySet()) {
                Property prop = entry.getValue();
                Field field = clazz.getDeclaredField(prop.getName());
                field.setAccessible(true);
                if (prop.getType().equals(ValueType.VAL)) {
                    Object value = Integer.parseInt(prop.getValue());
                    field.set(obj, value);
                } else {
                    if (prop.getType().equals(ValueType.REF)) {
                        String className = beanId.get(prop.getValue()).getClassName();
                        if (!objByClassName.containsKey(className)) {
                            Class classN = Class.forName(className);
                            if (isDepend(beanId.get(id).getClassName(), classN.getDeclaredFields())) {
                                throw new InvalidConfigurationException("No dependency");
                            } else {
                                Object value = getByClass(className);
                                field.set(obj, value);
                            }
                        } else {
                            field.set(obj, objByClassName.get(className));
                        }
                    }
                }
            }
            objByClassName.put(beanId.get(id).getClassName(), obj);
            objByName.put(beanId.get(id).getId(), obj);
            return obj;
        }
    }

    public boolean isDepend (String classN, Field[] declaredF) {
        for (Field field : declaredF) {
            if (field.getType().getName().equals(classN)) {
                return true;
            }
        }
        return false;
    }

    public Object getByClass(String className) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, InvalidConfigurationException, IllegalAccessException {
        if (objByClassName.containsKey(className)) {
            return objByClassName.get(className);
        } else {
            return getById(beanClass.get(className).getId());
        }
    }
}