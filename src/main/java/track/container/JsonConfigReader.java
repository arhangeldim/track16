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
        try {
            JsonNode rootNode = new ObjectMapper().readTree(configFile);
            return beansRootNode(rootNode);
        } catch (IOException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
    }

    private List<Bean> beansRootNode(JsonNode rootNode) throws InvalidConfigurationException {
        List<Bean> beanList = new ArrayList<>();
        Iterator iterator = rootNode.path("beans").elements();
        while (iterator.hasNext()) {
            JsonNode currentNode = (JsonNode) iterator.next();
            beanList.add(new Bean(currentNode.path("id").asText(), currentNode.path("className").asText(),
                    mapFromNode(currentNode.path("properties"))));
        }
        return beanList;
    }

    private Map<String, Property> mapFromNode(JsonNode node) throws InvalidConfigurationException {
        Map<String, Property> properties = new HashMap<>();
        Iterator iterator = node.elements();
        while (iterator.hasNext()) {
            JsonNode currentNode = (JsonNode) iterator.next();
            properties.put(currentNode.path("name").asText(), propertyFromNode(currentNode));
        }
        return properties;
    }

    private Property propertyFromNode(JsonNode node) throws InvalidConfigurationException {
        if (node.path("type").asText().equals("REF")) {
            return new Property(node.path("name").asText(), node.path("value").asText(), ValueType.REF);
        } else if (node.path("type").asText().equals("VAL")) {
            return new Property(node.path("name").asText(), node.path("value").asText(), ValueType.VAL);
        } else {
            throw new InvalidConfigurationException("neither val or ref fields are presented");
        }
    }
}
