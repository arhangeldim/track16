package track.container;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.Assert;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.lang.String;

import java.io.File;

import static org.apache.log4j.helpers.Loader.getResource;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws ValueException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidConfigurationException {

        /*

        ПРИМЕР ИСПОЛЬЗОВАНИЯ

         */

//        // При чтении нужно обработать исключение
        File file = new File(getResource("config.json").getFile());
        ConfigReader reader = new JsonConfigReader();
        try {
            List<Bean> beans = reader.parseBeans(file);
            //System.out.print(beans.toString());
            Container contain = new Container(beans);
            Engine engine = new Engine();
            engine.setPower(200);

            Gear gear = new Gear();
            gear.setCount(6);

            Car car = new Car();
            car.setEngine(engine);
            car.setGear(gear);

            Car newcar = (Car) contain.getById("carBean");
            System.out.println(newcar.toString());

        } catch (InvalidConfigurationException exception) {
            System.out.println(exception.getMessage());
        }



    }
}
