package track.container;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import java.io.File;
import java.util.List;

import track.container.config.Bean;

/**
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String path = "/home/oleg/test.json";
        ConfigReader reader = new JsonConfigReader();
        File file = new File(path);
        List<Bean> beans = null;
        try {
            beans = reader.parseBeans(file);
        } catch (InvalidConfigurationException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(beans);
        Container container = null;
        try {
            container = new Container(beans);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

/*
        Car car = (Car) container.getById("carBean");
        System.out.println(car);
        Gear gear = (Gear) container.getById("gearBean");
        System.out.println(gear.getCount());
        Engine engine = (Engine) container.getById("engineBean");
        System.out.println(engine.getPower());
*/
        Car car = (Car) container.getByClass("track.container.beans.Car");
        System.out.println(car);

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
