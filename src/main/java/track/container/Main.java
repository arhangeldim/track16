package track.container;

import track.container.beans.Car;
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
        /* Инициализируем класс, читающий конфиг, и считаем все бины */
        ConfigReader reader = new JsonConfigReader();
        File configFile = new File(Main.class.getClassLoader().getResource("config.json").getFile());

        List<Bean> beans;
        try {
            beans = reader.parseBeans(configFile);
        } catch (InvalidConfigurationException e) {
            System.out.println(e.toString());
            return;
        }

        /*  Инициализируем класс, отвечающий за десериализацию бинов,
            десериализуем бин Car и получим его двумя способами */
        Container container = new Container(beans);
        Car car = (Car) container.getByClass("track.container.beans.Car");
        System.out.println(car.toString());

        Car sameCar = (Car) container.getById("carBean");

        if (car != null && car.equals(sameCar)) {
            System.out.println("The car object requested by id is the same as the car object " +
                    "requested by the class name.");
        }
    }
}
