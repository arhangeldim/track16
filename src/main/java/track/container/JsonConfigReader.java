package track.container;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import track.container.config.*;

public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        List<Bean> beans = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(configFile);
            Iterator<String> rootNames = root.fieldNames();
            if (!rootNames.hasNext()) {
                throw new InvalidConfigurationException("file is empty");
            }
            String firstName = rootNames.next();
            if (!firstName.equals("beans")) {
                throw new InvalidConfigurationException("node should be 'beans'");
            }
            JsonNode beansNode = root.get("beans");
            if (!beansNode.getNodeType().toString().equals("ARRAY")) {
                throw new InvalidConfigurationException("'beans' is not ARRAY type");
            }
            for (Iterator<JsonNode> rawBeans = beansNode.iterator(); rawBeans.hasNext(); ) {
                JsonNode rawBean = rawBeans.next();
                Iterator<String> rawBeanNames = rawBean.fieldNames();
                if (    !rawBeanNames.next().equals("id") &&
                        !rawBeanNames.next().equals("class") &&
                        !rawBeanNames.next().equals("properties") &&
                         rawBeanNames.hasNext()) {
                    throw new InvalidConfigurationException("the bean has invalid fields");
                }
                String id = rawBean.get("id").asText();
                String className = rawBean.get("class").asText();
                Map<String, Property> properties = new HashMap<String, Property>();
                JsonNode propertiesNode = rawBean.get("properties");
                if (!propertiesNode.getNodeType().toString().equals("ARRAY")) {
                    throw new InvalidConfigurationException("'properties' node is not ARRAY");
                }
                for (Iterator<JsonNode> rawProperties = propertiesNode.iterator(); rawProperties.hasNext(); ) {
                    JsonNode property = rawProperties.next();
                    Iterator<String> propertyNames = property.fieldNames();
                    if (!propertyNames.next().equals("name")) {
                        throw new InvalidConfigurationException("first property fields is not 'id'");
                    }
                    String secondName = propertyNames.next();
                    ValueType type;
                    if (secondName.equals("val")) {
                        type = ValueType.VAL;
                    } else if (secondName.equals("ref")) {
                        type = ValueType.REF;
                    } else {
                        throw new InvalidConfigurationException("unknown property value");
                    }
                    if (propertyNames.hasNext()) {
                        throw new InvalidConfigurationException("property has more than 2 fields");
                    }
                    Iterator<JsonNode> propertyFields = property.iterator();
                    String name = propertyFields.next().asText();
                    String value = propertyFields.next().asText();
                    properties.put(name, new Property(name, value, type));
                }
                beans.add(new Bean(id, className, properties));
            }
            return beans;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
