package track.container.config;

import java.io.File;
import java.util.List;

/**
 *
 */
public interface ConfigReader {

    /*
    Все переменные, объявленные в интерфейсе по-умолчанию константы
     */
    String TAG_BEANS = "beans";
    String TAG_PROPERTIES = "properties";
    String ATTR_NAME = "name";
    String ATTR_VALUE = "val";
    String ATTR_REF = "ref";
    String ATTR_BEAN_ID = "id";
    String ATTR_BEAN_CLASS = "class";

    /**
     * Для методов интерфейса стоит писать документацию
     *
     *
     * @param configFile - Файл конфигурации (Сигнатуру не менять, используется в тестах)
     * @return - список бинов
     */
    List<Bean> parseBeans(File configFile) throws InvalidConfigurationException;

}
