package track.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zerts on 02.11.16.
 */

public class BeanXmlReader {
    private static final String TAG_BEAN = "bean";
    private static final String TAG_PROPERTY = "property";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_VALUE = "val";
    private static final String ATTR_REF = "ref";
    private static final String ATTR_BEAN_ID = "id";
    private static final String ATTR_BEAN_CLASS = "class";

    public List<Bean> parseBeans(String pathToFile) throws ParserConfigurationException, IOException, SAXException {
        File input = new File(pathToFile);
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
        Document doc = dbuilder.parse(input);

        NodeList beanList = doc.getElementsByTagName(TAG_BEAN);
        List<Bean> result = new ArrayList<>();


        for (int i = 0; i < beanList.getLength(); i++) {

            Node curr = beanList.item(i);
            Element elem = (Element) curr;
            String beanId = elem.getAttribute(ATTR_BEAN_ID);
            String beanClass = elem.getAttribute(ATTR_BEAN_CLASS);
            Map<String, Property> beanProperties = new HashMap<>();

            NodeList properties = elem.getElementsByTagName(TAG_PROPERTY);

            for (int j = 0; j < properties.getLength(); j++) {
                String name = ((Element) properties.item(j)).getAttribute(ATTR_NAME);

                ValueType type;
                String value;
                if (((Element) properties.item(j)).hasAttribute(ATTR_REF)) {
                    type = ValueType.REF;
                    value = ((Element) properties.item(j)).getAttribute(ATTR_REF);
                } else {
                    type = ValueType.VAL;
                    value = ((Element) properties.item(j)).getAttribute(ATTR_VALUE);
                }

                Property property = new Property(name, value, type);
                beanProperties.put(name, property);
            }

            result.add(new Bean(beanId, beanClass, beanProperties));
        }

        return result;
    }
}
