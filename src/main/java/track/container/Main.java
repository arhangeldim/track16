package track.container;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

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
        Container container = new Container(beans);
        Engine engine = (Engine) container.getById("engineBean");
        Car car = (Car) container.getByClass("track.container.beans.Car");
        Field field = Gear.class.getDeclaredField("count");
        field.setAccessible(true);
        System.out.println(Integer.TYPE.getName());
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
