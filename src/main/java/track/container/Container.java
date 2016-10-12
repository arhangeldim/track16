package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Container {

    private List<Bean> beans = new ArrayList<Bean>();
    private Map<String, Bean> map;
    private Map<String, Object> objByName = new HashMap<>();
    private Map<String, Object> objByClass = new HashMap<>();

    public Container(String file) throws Exception {

        BeanXmlReader bxr = new BeanXmlReader();
        List<Bean> beanList = bxr.parseBeans(file);

        beans = beanList;

//		Graph graph = new Graph(beanList);
//		List<Vertex> initList = graph.sort();

//		for (Vertex ver : initList) {
//			beans.add(ver.getBean());
//		}

        PropBeanMapper propBeans = new PropBeanMapper(beans);
        map = propBeans.getMap();

    }

    public List<Bean> getBeans() {
        return beans;
    }

    private Object process(Bean bean) throws Exception {

        Class beanClass = Class.forName(bean.getBeanClass());
        Object obj = beanClass.newInstance();

        for (Property prop : bean.getProperties()) {
            String methodName = "set" + prop.getName().substring(0,1).toUpperCase() + prop.getName().substring(1);
            Class[] paramTypes;
            Object[] args;

            if (prop.getValue() != null) {
                paramTypes = new Class[] { int.class };
                args = new Object[] { (int) Integer.valueOf(prop.getValue()) };

            } else {
                Bean childBean = map.get(prop.getReference());
                paramTypes = new Class[] { Class.forName(childBean.getBeanClass()) };
                args = new Object[] { process(childBean) };
            }
            Method setter = beanClass.getMethod(methodName, paramTypes);
            setter.invoke(obj, args);

        }

        objByClass.put(bean.getBeanClass(), obj);
        objByName.put(bean.getId(), obj);
        return obj;
    }

    public Object getByName(String objectName) {

        if (objByName.get(objectName) != null) {
            return objByName.get(objectName);
        }

        for (int i = 0; !beans.get(i).getId().equals(objectName); i++) {
            try {
                Object obj = process(beans.get(i));
                return obj;
            } catch (Exception exception) {
                System.out.println("Class doesn't exist.");
                return null;
            }
        }
        System.out.println("Object doesn't exist.");
        return null;
    }

    public Object getByClass(String className) {

        if (objByClass.get(className) != null) {
            return objByClass.get(className);
        }

        for (Bean bean : beans) {
            if (bean.getBeanClass().equals(className)) {
                try {
                    Object obj = process(bean);
                    return obj;
                } catch (Exception exception) {
                    System.out.println("Class doesn't exist.");
                    return null;
                }
            }
        }
        System.out.println("Object doesn't exist.");
        return null;
    }

}
