package track.container;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Root root = mapper.readValue(configFile, Root.class);
            return root.getBeans();
        } catch (Exception e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
    }
}
