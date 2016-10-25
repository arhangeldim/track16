package track.container;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 */

public class Main {


    public static void main(String[] args) {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */
        // При чтении нужно обработать исключение

        ConfigReader reader = new JsonConfigReader();
        List<Bean> beans = null;
        try {
            beans = reader.parseBeans(new File(Container.class.getClassLoader().getResource("config.json").getFile()));
            Container container = new Container(beans);
            Car car = (Car) container.getByClass("track.container.beans.Car");
            System.out.println(car);
            car = (Car) container.getById("carBean");
            System.out.println(car);
//            Engine engine = (Engine) container.getById("engineBean");
//            System.out.println(engine.getPower());

        } catch (InvalidConfigurationException | ClassNotFoundException | IllegalAccessException |
                InvocationTargetException | InstantiationException | NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(beans);


    }
}
