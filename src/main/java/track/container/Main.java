package track.container;

import org.xml.sax.SAXException;
import track.container.beans.Car;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Main {


    public static void main(String[] args) throws SAXException, ParserConfigurationException, InvalidConfigurationException, IOException {

        BeanXmlReader bxr = new BeanXmlReader();
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

        try {
            Car car2 = (Car) (container != null ? container.getByClass("track.container.beans.Car") : null);
            System.out.println(car2);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

        // При чтении нужно обработать исключение
        /*ConfigReader reader = new JsonConfigReader();
        ClassLoader classLoader = Container.class.getClassLoader();
        File file = new File(classLoader.getResource("config.json").getFile());
        List<Bean> beans = reader.parseBeans(file);
        System.out.println(beans);*/

        //Container container = new Container(beans);

        //Car car = (Car) container.getByClass("track.container.beans.Car");
        //car = (Car) container.getById("carBean");


    }
}
