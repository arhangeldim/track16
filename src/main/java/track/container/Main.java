package track.container;

import track.container.beans.Car;
import track.container.Container;
import track.container.beans.Gear;
import track.container.config.InvalidConfigurationException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws InvalidConfigurationException, IOException, ClassNotFoundException {

/*        Container container = new Container("/home/user/Documents/Git/track16/src/main/resources/config.json");
        Car car = (Car) container.getByClass("track.container.beans.Car");
        System.out.println(car);

        //Container cont = new Container("/home/user/Documents/Git/track16/src/main/resources/config.json");
        Gear gear = (Gear) container.getById("gearBean");
        System.out.println(gear);*/
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
