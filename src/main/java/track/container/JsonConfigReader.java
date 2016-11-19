package track.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;


/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {
    private void checkForRequiredFields(JsonNode node, List<String> requiredFields) throws InvalidConfigurationException {
        for (String requiredField: requiredFields) {
            if (!node.has(requiredField)) {
                throw new InvalidConfigurationException("Field " + requiredField + " is required.");
            }
        }
    }



    /**
     * Распарсить Property из JsonNode
     */
    private Property parseProperty(JsonNode property) throws InvalidConfigurationException {
        checkForRequiredFields(property, Arrays.asList("name", "value", "type"));
        String name = property.get("name").asText();
        String value = property.get("value").asText();
        ValueType type;
        if (property.get("type").asText().equals("REF")) {
            type = ValueType.REF;
        } else {
            type = ValueType.VAL;
        }
        return new Property(name, value, type);
    }


    /**
     * Распарсить один бин из JsonNode
     */

    private Bean parseBean(JsonNode bean) throws InvalidConfigurationException {
        checkForRequiredFields(bean, Arrays.asList("id", "className", "properties"));
        String id = bean.get("id").asText();
        String className = bean.get("className").asText();
        Map<String, Property> properties = new HashMap();
        for (JsonNode property: bean.get("properties")) {
            Property parsedProperty = parseProperty(property);
            properties.put(parsedProperty.getName(), parsedProperty);
        }
        return new Bean(id, className, properties);
    }


    /**
     * Распарсить бины из конфига в список бинов
     */
    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(configFile);

        checkForRequiredFields(root, Collections.singletonList("beans"));
        List<Bean> beans = new ArrayList();
        for (JsonNode bean: root.get("beans")) {
            beans.add(parseBean(bean));
        }
        return beans;
    }
}
