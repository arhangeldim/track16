package track.container;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {


    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        List<Bean> beansList = new ArrayList<Bean>();
        try {
            Iterator<JsonNode> nodeIterator = new ObjectMapper().readTree(configFile).path("beans").elements();
            while (nodeIterator.hasNext()) {
                JsonNode currentNode = nodeIterator.next();
                beansList.add(new Bean(currentNode.path("id").asText(), currentNode.path("className").asText(),
                        asMap(currentNode.path("properties"))));

            }
        } catch (IOException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
        return beansList;
    }

    private Map<String, Property> asMap(JsonNode jsonNode) throws InvalidConfigurationException {
        Map<String, Property> properties = new HashMap<>();
        Iterator<JsonNode> jsonNodeIterator = jsonNode.elements();
        while (jsonNodeIterator.hasNext()) {
            JsonNode currentNode = jsonNodeIterator.next();
            if (currentNode.has("ref")) {
                properties.put(currentNode.path("name").asText(),new Property(currentNode.path("name").asText(),
                        currentNode.path("ref").asText(), ValueType.REF));
            } else if (currentNode.has("val")) {
                properties.put(currentNode.path("name").asText(), new Property(currentNode.path("name").asText(),
                        currentNode.path("val").asText(), ValueType.VAL));
            } else {
                throw new InvalidConfigurationException("Invalid configuration");
            }
        }
        return properties;
    }
}
