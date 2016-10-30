package track.container;

import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
        ConfigReader reader = new JsonConfigReader();
        try {
            File file = new File(JsonConfigReader.class.getClassLoader().getResource("config.json").getFile());
            List<Bean> beans = reader.parseBeans(file);
            System.out.println(beans);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");


    }
}
