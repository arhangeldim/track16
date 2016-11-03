package track.container;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;
import track.container.config.InvalidConfigurationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.ConfigReader;


/**
 * Class implements parsing .json file to a list of beans.
 * 
 * @author Sergey Ivanychev
 */
class JsonConfigReader implements ConfigReader {
    private static final String BEANS_ATTR = "beans";
    private static final String ID_ATTR = "id";
    private static final String CLASSNAME_ATTR = "className";
    private static final String PROPERTIES_ATTR = "properties";
    private static final String NAME_ATTR = "name";
    private static final String VAL_ATTR = "value";
    private static final String TYPE_ATTR = "type";
    private static final String TYPE_VAL = "VAL";

    /**
     * Checks internal structure of tree, than was created after .json parsing
     * by Jackson
     * 
     * @param tree  root of the tree
     * @return  true, if the structure is correct, false otherwise
     */
    private boolean verifyTree(JsonNode tree) {
        return tree.has(BEANS_ATTR) && tree.get(BEANS_ATTR).isArray();
    }

    /**
     * Checks internal structure of property structure. It must contain three fields
     * @param node  root of a property subtree
     * @return true, if the structure is correct, false otherwise
     */
    private boolean verifyProperty(JsonNode node) {
        return node.has(NAME_ATTR) && node.has(TYPE_ATTR) && node.has(VAL_ATTR);
    }

    /**
     * Checks internal structure of a bean subtree. Each bean must contain ID, name of a class 
     * and properties structure. 
     * 
     * @param node the root of a bean subtree
     * @return true, if the structure is correct, false otherwise
     */
    private boolean verifyBeanNode(JsonNode node) {
        boolean ok = node.has(ID_ATTR) &&
                node.has(CLASSNAME_ATTR) &&
                node.has(PROPERTIES_ATTR);
        JsonNode properties = node.get(PROPERTIES_ATTR);
        Iterator<String> it = properties.fieldNames();
        while (it.hasNext()) {
            String key = it.next();
            ok = ok && verifyProperty(properties.get(key));
        }
        return ok;
    }

    /**
     * Converts property subtree to Property object.
     * @param propertyNode a property subtree
     * @return Property object
     */
    private Property getProperty(JsonNode propertyNode) {
        String name = propertyNode.get(NAME_ATTR).textValue();
        String typeName = propertyNode.get(TYPE_ATTR).textValue();
        ValueType type = (typeName.equals(TYPE_VAL)) ? ValueType.VAL : ValueType.REF;
        String value = propertyNode.get(VAL_ATTR).asText();
        return new Property(name, value, type);
    }

    /**
     * Extracts properties of properties subtree.
     *
     * @param propertiesNode a properties subtree
     * @return Map, where keys are property names and values are Property objects.
     */
    private HashMap<String, Property> getProperties(JsonNode propertiesNode) {
        HashMap<String, Property> ret = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> it = propertiesNode.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> next = it.next();
            ret.put(next.getKey(), getProperty(next.getValue()));
        }
        return ret;
    }

    /**
     * Processes a bean subtree.
     *
     * @param node a brean subtree
     * @return Bean object
     * @throws InvalidConfigurationException if bean structure is corrupted
     */
    private Bean process(JsonNode node) throws InvalidConfigurationException {
        boolean ok = verifyBeanNode(node);
        if (!ok) {
            throw new InvalidConfigurationException("Invalid bean");
        }

        String id = node.get(ID_ATTR).textValue();
        String className = node.get(CLASSNAME_ATTR).textValue();
        HashMap<String, Property> properties = getProperties(node.get(PROPERTIES_ATTR));
        return new Bean(id, className, properties);
    }

    /**
     * JSON with beans list is processed into list of beans.
     * @param configFile readable file descriptor of a JSON
     * @return list of Bean objects
     * @throws InvalidConfigurationException is thrown if there is IO error of JSON is not ok.
     */
    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree;
        try {
            tree = mapper.readTree(configFile);
        } catch (IOException e) {
            throw new InvalidConfigurationException("IO error: " + e.getMessage());
        }
        if (!verifyTree(tree)) {
            throw new InvalidConfigurationException("Bad JSON");
        }
        List<Bean> beans = new ArrayList<>();
        JsonNode beansNodes = tree.get(BEANS_ATTR);
        for (JsonNode beanNode: beansNodes) {
            beans.add(process(beanNode));
        }
        return beans;
    }
}

