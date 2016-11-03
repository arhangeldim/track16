package track.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();
        List<Bean> beans = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(configFile).path("beans");
            for (JsonNode currentNode : root) {
                String id = currentNode.path("id").asText();
                String className = currentNode.path("className").asText();
                Map<String, Property> properties = makePropertyMap(currentNode.path("properties"));
                beans.add(new Bean(id, className, properties));
            }
            return beans;
        } catch (IOException e) {
            throw new InvalidConfigurationException("Invalid configuration");
        }
    }

    private Map<String, Property> makePropertyMap(JsonNode jsonNode) throws InvalidConfigurationException {
        Map<String, Property> properties = new HashMap<>();
        for (JsonNode currentNode: jsonNode) {
            String propName = currentNode.path("name").asText();
            Property property = null;
            if (currentNode.path("type").asText().equals("REF")) {
                property = new Property(propName, currentNode.path("value").asText(), ValueType.REF);
            } else if (currentNode.path("type").asText().equals("VAL")) {
                property = new Property(propName, currentNode.path("value").asText(), ValueType.VAL);
            } else {
                throw new InvalidConfigurationException("Can't find value for " + propName);
            }
            properties.put(propName, property);
        }
        return properties;
    }
}
