package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import org.mockito.internal.matchers.Null;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class Container {

    private List<Bean> beans = new ArrayList<Bean>();
    private Map<String, Bean> map;
    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClass = new HashMap<>();
    private Map<String, Integer> generated = new HashMap<>();

    public Container(String file) throws Exception {

        BeanXmlReader bxr = new BeanXmlReader();
        List<Bean> beanList = bxr.parseBeans(file);

//        Graph graph = new Graph(beanList);
//        List<Vertex> initList = graph.sort();

        //  Граф проверяет наличие циклов и плохих штук

//        for (Vertex ver : initList) {
//            beans.add(ver.getBean());
//        }

        beans = beanList;
        PropBeanMapper propBeans = new PropBeanMapper(beans);
        map = propBeans.getMap();

    }

    public List<Bean> getBeans() {
        return beans;
    }

    private Object process(Bean bean) throws Exception {

        if (generated.get(bean.getId()) == null) {
            generated.put(bean.getId(), 0);
        } else {
            System.out.println("Cycle!");
            throw new Exception();
        }
        Class beanClass;
        try {
            beanClass = Class.forName(bean.getBeanClass());
        } catch (ClassNotFoundException exception) {
            System.out.println("Class doesn't exist.");
            throw (exception);
        }
        Object obj = beanClass.newInstance();

        for (Property prop : bean.getProperties()) {
            String methodName = "set" + prop.getName().substring(0,1).toUpperCase() + prop.getName().substring(1);
            Class[] paramTypes;
            Object[] args;

            if (prop.getValue() != null) {
                Field field = beanClass.getDeclaredField(prop.getName());
                try {
                    Constructor constructor = field.getType().getConstructor(String.class);
                    paramTypes = new Class[] { field.getType() };
                    args = new Object[] { constructor.newInstance(prop.getValue()) };
                } catch (NoSuchMethodException exception) {
                    System.out.println("No string constructor.");
                    throw (exception);
                } catch (Exception exception) {
                    System.out.println("Wrong data format.");
                    throw (exception);
                }
            } else {
                Bean childBean = map.get(prop.getReference());
                paramTypes = new Class[] { Class.forName(childBean.getBeanClass()) };
                args = new Object[] { process(childBean) };
            }
            Method setter = beanClass.getMethod(methodName, paramTypes);
            setter.invoke(obj, args);
        }

        generated.put(bean.getId(), 1);
        objByClass.put(bean.getBeanClass(), obj);
        objByName.put(bean.getId(), obj);
        return obj;
    }

    public Object getByName(String objectName) throws Exception {

        if (objByName.get(objectName) != null) {
            return objByName.get(objectName);
        }

        for (Bean bean : beans) {
            if (bean.getId().equals(objectName)) {
                try {
                    Object obj = process(bean);
                    return obj;
                } catch (Exception exception) {
                    throw exception;
                }
            }
        }

        System.out.println("Object doesn't exist.");
        return null;
    }

    public Object getByClass(String className) throws Exception  {

        if (objByClass.get(className) != null) {
            return objByClass.get(className);
        }

        for (Bean bean : beans) {
            if (bean.getBeanClass().equals(className)) {
                try {
                    Object obj = process(bean);
                    return obj;
                } catch (Exception exception) {
                    throw exception;
                }
            }
        }

        System.out.println("Object doesn't exist.");
        return null;
    }

}
