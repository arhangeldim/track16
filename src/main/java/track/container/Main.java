package track.container;


import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws InvalidConfigurationException {
        File file = new File("src\\main\\resources\\config.json");
        List<Bean> beans = new JsonConfigReader().parseBeans(file);
        for (Bean bean: beans) {
            System.out.println(bean.toString());
        }

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
//         ConfigReader reader = new JsonReader();
//          List<Bean> beans = reader.parseBeans("config.json");
//        Container container = new Container(beans);
//
//        Car car = (Car) container.getByClass("track.container.beans.Car");
//        car = (Car) container.getById("carBean");
        Container container = new Container(beans);
        try {
            Gear gear = (Gear) container.getById("gearBean");
            System.out.print(gear);
            Engine engine = (Engine) container.getById("engineBean");
            System.out.print(engine);
            Car car = (Car) container.getById("carBean");
            System.out.print(car);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
