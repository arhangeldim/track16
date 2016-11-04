package track.container;

import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */
            // При чтении нужно обработать исключение
        ConfigReader reader = new JsonConfigReader();
        ClassLoader classLoader = Container.class.getClassLoader();
        File file = new File(classLoader.getResource("config.json").getFile());
        try {
            List<Bean> beans = reader.parseBeans(file);
            Container container = new Container(beans);
            System.out.println(beans);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        //Container container = new Container(beans);

        //Car car = (Car) container.getByClass("track.container.beans.Car");
        //car = (Car) container.getById("carBean");


    }
}
