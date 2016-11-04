package track.container;

//import track.container.JsonConfigReader;
import track.container.config.ConfigReader;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {
//        // При чтении нужно обработать исключение
        ConfigReader reader = new JsonConfigReader();
        File configFile = new File("config.json");
        try {
            List<Bean> beans = reader.parseBeans(configFile);
        } catch (InvalidConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");


    }
}
