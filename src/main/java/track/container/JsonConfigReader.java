package track.container;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();
        List<Bean> bean = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(configFile).path("bean");
            for (JsonNode indexNode : root) {
                String id = indexNode.path("id").asText();
                String className = indexNode.path("className").asText();
                Map<String, Property> properties = createProperty(indexNode.path("properties"));
                bean.add(new Bean(id, className, properties));
            }
            return bean;
        } catch (IOException e) {
            throw new InvalidConfigurationException("Invalid config");
        }
    }

    public Map<String, Property> createProperty(JsonNode jsonNode) throws InvalidConfigurationException {
        Map<String, Property> properties = new HashMap<>();
        for (JsonNode indexNode : jsonNode) {
            String name = indexNode.path("name").asText();
            Property property = null;
            if (indexNode.has("ref")) {
                property = new Property(name, indexNode.path("ref").asText(), ValueType.REF);
            } else {
                if (indexNode.has("val")) {
                    property = new Property(name, indexNode.path("val").asText(), ValueType.VAL);
                } else {
                    throw new InvalidConfigurationException("Not value");
                }
            }
            properties.put(name, property);
        }
        return properties;
    }
}