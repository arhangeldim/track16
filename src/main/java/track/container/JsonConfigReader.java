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

    private Property parseProperty(JsonNode property) throws InvalidConfigurationException {
        String name = property.get("name").asText();
        ValueType type;
        String value = property.get("value").asText();
        if (property.get("type").asText().equals("REF")) {
            type = ValueType.REF;
        } else {
            type = ValueType.VAL;
        }

        return new Property(name, value, type);
    }

    private Bean parseBean(JsonNode bean) throws InvalidConfigurationException {
        String id = bean.get("id").asText();
        String className = bean.get("className").asText();
        Map<String, Property> properties = new HashMap();
        for (JsonNode property: bean.get("properties")) {
            Property parsedProperty = parseProperty(property);
            properties.put(parsedProperty.getName(), parsedProperty);
        }

        return new Bean(id, className, properties);
    }

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Bean> beans = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(configFile);
            for (JsonNode bean : root.get("beans")) {
                beans.add(parseBean(bean));
            }
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return beans;
    }
}