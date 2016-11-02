package track.container.config;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface ConfigReader {

    /**
     * Для методов интерфейса стоит писать документацию
     *
     *
     * @param configFile - Файл конфигурации (Сигнатуру не менять, используется в тестах)
     * @return - список бинов
     */
    List<Bean> parseBeans(File configFile) throws InvalidConfigurationException, IOException, SAXException, ParserConfigurationException;

}
