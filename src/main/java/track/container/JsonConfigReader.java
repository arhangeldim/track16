package track.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
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
                Map<String, Property> properties = createPropertyMap(currentNode.path("properties"));
                beans.add(new Bean(id, className, properties));
            }
            return beans;
        } catch (IOException e) {
            throw new InvalidConfigurationException("Invalid configuration");
        }
    }

    private Map<String, Property> createPropertyMap(JsonNode jsonNode) throws InvalidConfigurationException {
        Map<String, Property> properties = new HashMap<>();
        for (JsonNode currentNode : jsonNode) {
            String name = currentNode.path("name").asText();
            Property property = null;
            if (currentNode.has("ref")) {
                property = new Property(name, currentNode.path("ref").asText(), ValueType.REF);
            } else if (currentNode.has("val")) {
                property = new Property(name, currentNode.path("val").asText(), ValueType.VAL);
            } else {
                throw new InvalidConfigurationException("There are not value for " + name);
            }
            properties.put(name, property);
        }
        return properties;
    }
}
