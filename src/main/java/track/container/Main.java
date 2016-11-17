package track.container;

import track.container.beans.Car;
import track.container.config.*;


import java.io.File;
import java.util.List;

/**
 *
 */
public class Main {

    public static void main(String[] args) {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */
        String path = "config.json";
        ConfigReader reader = new JsonConfigReader();
        File file = new File(Main.class.getClassLoader().getResource("config.json").getFile());
        try {
            List<Bean> beans = reader.parseBeans(file);
            for (Bean bean : beans) {
                System.out.println(bean);
            }
            Container container = new Container(beans);

            Car car = (Car) container.getByClass("track.container.beans.Car");
            System.out.println(car);
            car = (Car) container.getById("carBean");
            System.out.println(car);
        } catch (InvalidConfigurationException e) {
            System.out.println(e.getMessage());
        }



    }
}
