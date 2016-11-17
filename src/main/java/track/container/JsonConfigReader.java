package track.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;


public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser;
        List<Bean> beans = new ArrayList<>();
        try {
            parser = factory.createParser(configFile);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Config not found or corrupted");
        }
        while (!parser.isClosed()) {
            JsonToken token;
            try {
                token = parser.nextToken();
            } catch (IOException e) {
                throw new InvalidConfigurationException("Incorrect token in config");
            }
            if (token == null) {
                break;
            }

            try {
                //looking for root object
                if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName().equals("beans")) {
                    token = parser.nextToken();
                    // checking for start array token
                    if (!JsonToken.START_ARRAY.equals(token)) {
                        break;      //empty or incorrect config
                    }
                    //if next token not START_OBJECT nothing will be read
                    while (parser.nextToken().equals(JsonToken.START_OBJECT)) {
                        String id = null;
                        token = parser.nextToken();
                        if (token.equals(JsonToken.FIELD_NAME) && parser.getText().equals("id")) {
                            token = parser.nextToken();
                            if (token.equals(JsonToken.VALUE_STRING)) {
                                id = parser.getText();
                            }
                        }
                        String className = null;
                        token = parser.nextToken();
                        if (token.equals(JsonToken.FIELD_NAME) && parser.getText().equals("class")) {
                            token = parser.nextToken();
                            if (token.equals(JsonToken.VALUE_STRING)) {
                                className = parser.getText();
                            }
                        }
                        Map<String, Property> properties = new HashMap<>();
                        token = parser.nextToken();
                        if (token.equals(JsonToken.FIELD_NAME) && parser.getText().equals("properties")) {
                            token = parser.nextToken();
                            if (token.equals(JsonToken.START_ARRAY)) {

                                while (parser.nextToken().equals(JsonToken.START_OBJECT)) {
                                    token = parser.nextToken();
                                    String propertyName = null;
                                    if (token.equals(JsonToken.FIELD_NAME) && parser.getText().equals("name")) {
                                        token = parser.nextToken();
                                        if (token.equals(JsonToken.VALUE_STRING)) {
                                            propertyName = parser.getText();
                                        }
                                    }
                                    token = parser.nextToken();
                                    String propertyType = null;
                                    String propertyValue = null;
                                    if (token.equals(JsonToken.FIELD_NAME) &&
                                            (parser.getText().equals("ref") | parser.getText().equals("val"))) {
                                        propertyType = parser.getText();
                                        token = parser.nextToken();
                                        if (token.equals(JsonToken.VALUE_STRING)) {
                                            propertyValue = parser.getText();
                                        }
                                    }
                                    if (propertyName != null & propertyType != null & propertyValue != null) {
                                        Property property = new Property(
                                                propertyName,
                                                propertyValue,
                                                ValueType.valueOf(propertyType.toUpperCase())
                                        );
                                        properties.put(propertyName, property);
                                    }
                                    parser.nextToken(); //to end object token
                                }
//                                token = parser.nextToken();     //to end array token
                            }
                        }
                        //is bean with empty properties possible?
                        if (id != null & className != null & !properties.isEmpty()) {
                            beans.add(new Bean(id, className, properties));
                        }
                        parser.nextToken();         //to end object token
                    }
                }
            } catch (IOException e) {
                throw new InvalidConfigurationException("Incorrect config");
            }
        }

        return beans;
    }
}
