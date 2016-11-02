package track.container;

import org.xml.sax.SAXException;
import track.container.beans.Car;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Main {


    public static void main(String[] args) {

        BeanXmlReadel bxr = new BeanXmlReadel();
        try {


            List<Bean> beans = bxr.parseBeans("src/main/resources/test.xml");
            //beans.forEach(System.out::println);
            Graph gr = new Graph(beans);
            gr.sort().forEach(x -> System.out.println(x.getId()));



        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        Car carr = new Car();
        System.out.println(carr.getClass());
        
        Container container = null;
        try {
            container = new Container("src/main/resources/test.xml");
            Car car = (Car) container.getById("carBean");
            System.out.println(car);
        } catch (InvalidConfigurationException | IllegalAccessException |
                InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
