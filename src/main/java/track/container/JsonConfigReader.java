package track.container;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

import static sun.misc.Version.print;

public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(configFile);
            List<Bean> beanList = new ArrayList<>();
            for (JsonNode node : jsonNode.path("beans")) {
                Map<String, Property> properties = new HashMap<>();
                String id = node.path("id").asText();
                String className = node.path("className").asText();
                Bean bean = new Bean(id, className, propertiesToMap(node.path("properties")));
                beanList.add(bean);
            }
            return beanList;

        } catch (IOException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
    }

    private Map<String, Property> propertiesToMap(JsonNode node) throws InvalidConfigurationException {
        Map<String, Property> propertiesMap = new HashMap<>();
        for (JsonNode subNode : node) {
            String name = subNode.path("name").asText();
            Property property = null;
            if (subNode.has("ref")) {
                property = new Property(name, subNode.path("ref").asText(), ValueType.REF);

            }

            if (subNode.has("val")) {
                property = new Property(name, subNode.path("val").asText(), ValueType.VAL);
            }

            if (property != null) {
                propertiesMap.put(name, property);
            } else {
                throw new InvalidConfigurationException("There is no 'ref' or 'val' field");
            }
        }
        return propertiesMap;
    }
}
