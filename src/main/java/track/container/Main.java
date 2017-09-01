package track.container;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws InvalidConfigurationException {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
        ConfigReader reader = new JsonConfigReader();
        List<Bean> beans = reader.parseBeans(new File("/home/ksushar/config.json"));
        Container container = new Container(beans);
        System.out.println(beans);
//
        Car car = (Car) container.getByClass("track.container.beans.Car");
        car = (Car) container.getById("carBean");


    }
}
