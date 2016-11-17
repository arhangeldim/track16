package track.container;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.*;

/**
 * TODO: Реализовать
 */
public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Root root;
        try {
            root = mapper.readValue(configFile,Root.class);
        } catch (IOException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }

        return root.getBeans();
    }
}
