package track.container;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;

/**
 *
 */
public class ContainerTest {


    private static Container container;

    private static Car expectedCar;
    private static Gear expectedGear;
    private static Engine expectedEngine;

    @BeforeClass
    public static void init() {
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
        //expectedEngine.setPower(200);

        expectedGear = new Gear();
        //expectedGear.setCount(6);

        expectedCar = new Car();
        expectedCar.setEngine(expectedEngine);
        expectedCar.setGear(expectedGear);

    }

    @Test
    public void testGetByName() throws Exception {
        Car car = (Car) container.getById("carBean");
        Assert.assertTrue(car != null);
        Assert.assertEquals(expectedCar, car);
    }

    @Test
    public void testGetByClass() throws Exception {
        Car car = (Car) container.getByClass("track.container.beans.Car");
        Assert.assertTrue(car != null);
        Assert.assertEquals(expectedCar, car);
    }

}