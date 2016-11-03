package track.container;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    private Property parseProperty(JsonNode property) throws InvalidConfigurationException {
        if (!property.has("name")) {
            throw new InvalidConfigurationException("There is no field \"name\" in property" +
                    ", but it must be here");
        }
        if (!(property.has("ref") ^ property.has("val"))) {
            throw new InvalidConfigurationException("Wrong fields \"ref\" or \"val\": one and only one " +
                    "of them must be in property");
        }

        String name = property.get("name").asText();

        ValueType type = null;
        String value = null;
        if (property.has("ref")) {
            type = ValueType.REF;
            value = property.get("ref").asText();
        } else {
            type = ValueType.VAL;
            value = property.get("val").asText();
        }

        return new Property(name, value, type);
    }

    private Bean parseBean(JsonNode bean) throws InvalidConfigurationException {
        if (!bean.has("id")) {
            throw new InvalidConfigurationException("There is no field \"id\", but it must be here");
        }
        if (!bean.has("className")) {
            throw new InvalidConfigurationException("There is no field \"className\", but it must be here");
        }
        if (!bean.has("properties")) {
            throw new InvalidConfigurationException("There is no field \"properties\", but it must be here");
        }

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
        JsonNode root = null;
        try {
            root = objectMapper.readTree(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!root.has("beans")) {
            throw new InvalidConfigurationException("There is no field \"beans\", but it must be here");
        }
        List<Bean> beans = new ArrayList();

        for (JsonNode bean: root.get("beans")) {
            beans.add(parseBean(bean));
        }

        return beans;
    }
}
