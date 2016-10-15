package track.container.config;

import java.io.File;
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
    List<Bean> parseBeans(File configFile) throws InvalidConfigurationException;

}
