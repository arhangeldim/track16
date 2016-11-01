package track.container;

import track.container.beans.Car;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;
import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConfigReader reader = new JsonConfigReader();
        try {
            File file = new File(JsonConfigReader.class.getClassLoader().getResource("config.json").getFile());
            List<Bean> bean = reader.parseBeans(file);
            System.out.println(bean);
            Container container = new Container(bean);
            Car car = (Car) container.getByClass("track.container.beans.Car");
            car = (Car) container.getById("carBean");
            System.out.println(car);
         } catch (Exception e) {
            e.printStackTrace();
         }
    }
}
