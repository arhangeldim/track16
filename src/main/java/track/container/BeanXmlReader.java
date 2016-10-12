package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

class BeanXmlReader {

    private static String pathToResourses;
    private static final String TAG_BEAN = "bean";
    private static final String TAG_PROPERTY = "property";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_VALUE = "val";
    private static final String ATTR_REF = "ref";
    private static final String ATTR_BEAN_ID = "id";
    private static final String ATTR_BEAN_CLASS = "class";

    public BeanXmlReader() {
        pathToResourses = "src/main/resources/";
    }

    public BeanXmlReader(String pathToResourses) {
        this.pathToResourses = pathToResourses;
    }

    public void visit(Node node, int level) {
        System.out.println(level);
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child instanceof Element) {
                Element childElement = (Element) child;
                System.out.println(childElement.getAttribute("name"));
            }
            visit(child, level + 1);
        }
    }

    public List<Bean> parseBeans(String fileName) throws Exception {

        String pathToFile = this.pathToResourses + fileName;
        File file = new File(pathToFile);

        List<Bean> list = new ArrayList<Bean>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList root = document.getChildNodes();
        NodeList beans = root.item(0).getChildNodes();

        for (int i = 0; i < beans.getLength(); i++) {
            Node bean = beans.item(i);
            if (bean instanceof Element) {
                Element beanElement = (Element) bean;
                Bean beanInstance = new Bean();
                beanInstance.setId(beanElement.getAttribute(ATTR_BEAN_ID));
                beanInstance.setClass(beanElement.getAttribute(ATTR_BEAN_CLASS));

                NodeList propertyNodes = bean.getChildNodes();
                List<Property> properties = new ArrayList<Property>();
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node property = propertyNodes.item(j);
                    if (property instanceof Element) {
                        Element propElement = (Element) property;
                        Property propInstance = new Property();

                        propInstance.setName(propElement.getAttribute(ATTR_NAME));

                        if (propElement.hasAttribute(ATTR_VALUE)) {
                            propInstance.setValue(propElement.getAttribute(ATTR_VALUE));
                        }

                        if (propElement.hasAttribute(ATTR_REF)) {
                            propInstance.setReference(propElement.getAttribute(ATTR_REF));
                        }

                        properties.add(propInstance);
                    }
                }

                beanInstance.setProperties(properties);
                list.add(beanInstance);
            }
        }

        return list;
    }

}
