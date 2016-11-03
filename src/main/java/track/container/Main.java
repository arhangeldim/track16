package track.container;

import track.container.beans.Car;
import track.container.config.InvalidConfigurationException;

import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

        // При чтении нужно обработать исключение

        Container container = null;
        try {
            container = new Container("config.json");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
//
        Car car = (Car) container.getByClass("track.container.beans.Car");
        car = (Car) container.getById("carBean");


    }
}
