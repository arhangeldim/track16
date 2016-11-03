package track.container;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

import java.io.File;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) {

        JsonConfigReader reader = new JsonConfigReader();
        File file = new File("/Users/iv/code/track16/src/main/resources/config.json");
        List<Bean> beans = null;
        try {
            beans = reader.parseBeans(file);
        } catch (InvalidConfigurationException e) {
            System.out.println(e.getMessage());
            return;
        }

        Container container = new Container(beans);
//        Engine engine = (Engine) container.getByClass("track.container.beans.Engine");
//        Car car = (Car) container.getById("carBean");
//        System.out.print(beans.toString());
    }
}
