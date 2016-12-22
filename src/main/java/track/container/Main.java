package track.container;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.apache.log4j.helpers.Loader.getResource;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws ValueException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidConfigurationException {

        /*
        ПРИМЕР ИСПОЛЬЗОВАНИЯ
         */

//        // При чтении нужно обработать исключение
        File file = new File(getResource("config.json").getFile());
        ConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(file);
            System.out.print(beans.toString());

        } catch (InvalidConfigurationException exception) {
            System.out.println(exception.getMessage());
        }



    }
}