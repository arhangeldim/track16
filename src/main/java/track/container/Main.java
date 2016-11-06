package track.container;

import track.container.config.ConfigReader;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;

import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        // При чтении нужно обработать исключение
        ConfigReader reader = new JsonConfigReader();
        File configFile = new File("src/main/java/track/container/config.json");
        try {
            List<Bean> beans = reader.parseBeans(configFile);
            Container container = new Container(beans);
            System.out.println(container);
            Car car = (Car) container.getByClass("track.container.beans.Car");
            System.out.println(container);
            //System.out.println(car.toString());
            Engine engine = (Engine) container.getByClass("track.container.beans.Engine");
            System.out.println(container);
           // System.out.println(engine.toString());
            Gear gear = (Gear) container.getByClass("track.container.beans.Gear");
            System.out.println(container);
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }
}
