package track.container;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import track.container.config.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {

        byte[] file = new byte[0];
        try {
            file = Files.readAllBytes(configFile.toPath());
        } catch (IOException e) {
            e.printStackTrace(); /// We won't get Hear because File consist
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, JsonNode> beans = null;
        try {
            beans = objectMapper.readValue(file, new TypeReference<Map<String, JsonNode>>() {
            });
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid config");
        }
        ArrayList<Bean> list = new ArrayList<Bean>();
        for (JsonNode node : beans.get("beans")) {
            if (!node.has("className") || !node.has("id") || !node.has("properties")) {
                throw new InvalidConfigurationException("There's no id or className or Properties");
            }
            String className = convert(node.get("className").toString());
            String id = convert(node.get("id").toString());
            HashMap<String, Property> newProperties = new HashMap<String, Property>();


            for (JsonNode properties : node.get("properties")) {
                Property property = new Property();

                if (!properties.has("name") || !properties.has("value") || !properties.has("type")) {
                    throw new InvalidConfigurationException("property has't got name or (value or type)");
                }

                property.setName(convert(properties.get("name").toString()));
                if (convert(properties.get("type").toString()).equals("VAL")) {
                    property.setType(ValueType.VAL);
                }
                else
                {
                    property.setType(ValueType.REF);
                }
                property.setValue(convert(properties.get("value").toString()));
                newProperties.put(convert(property.getName()), property);
            }
            Bean bean = new Bean(id, className, newProperties);
            list.add(bean);
        }
        return list;

    }

    private String convert(String str) {
        if(str.startsWith("\""))
            return str.substring(1, str.length() - 1);
        else
            return str;
    }
}
