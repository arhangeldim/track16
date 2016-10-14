package track.container;

import org.junit.Test;
import track.container.config.Bean;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Тест имплементации ConfigReader
 */
public class JsonConfigReaderTest {
    @Test
    public void parseBeans() throws Exception {
        File configFile = new File(JsonConfigReaderTest.class.getClassLoader().getResource("config.json").getFile());

        JsonConfigReader jsonConfigReader = new JsonConfigReader();
        List<Bean> beanList = jsonConfigReader.parseBeans(configFile);
        assertNotNull(beanList);

        String[] beans = {"carBean", "gearBean", "engineBean"};
        for (int i = 0; i < beanList.size(); i++) {
            assertEquals(beans[i], beanList.get(i).getId());
        }
    }

}