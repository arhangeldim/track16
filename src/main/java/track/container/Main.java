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

        ConfigReader configReader = new JsonConfigReader();
        List<Bean> beans;
        try {
            beans = configReader.parseBeans(
                    new File("src\\main\\resources\\config.json"));
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }
        for (Bean bean : beans)
        System.out.println(bean);

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
//        ConfigReader reader = new JsonReader();
//        List<Bean> beans = reader.parseBeans("config.json");
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");


    }
}
