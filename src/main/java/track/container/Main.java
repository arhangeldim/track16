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
        Container container = null;

        Car expectedCar;
        Gear expectedGear;
        Engine expectedEngine;
//        // При чтении нужно обработать исключение
        try {
            ClassLoader classLoader = Container.class.getClassLoader();
            File file = new File(classLoader.getResource("config.json").getFile());
            ConfigReader reader = new JsonConfigReader();
            container = new Container(reader.parseBeans(file));
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(container != null);

        expectedEngine = new Engine();
        expectedEngine.setPower(200);

        expectedGear = new Gear();
        expectedGear.setCount(6);

        expectedCar = new Car();
        expectedCar.setEngine(expectedEngine);
        expectedCar.setGear(expectedGear);

    }
}
