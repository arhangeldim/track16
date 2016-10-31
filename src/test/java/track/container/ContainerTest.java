package track.container;

<<<<<<< HEAD
import org.junit.Assert;
import org.junit.Test;
import track.container.beans.Car;
import track.container.beans.Gear;

/**
 * Created by geoolekom on 12.10.16.
 */
public class ContainerTest {

    @Test
    public void cyclesTest() throws Exception {
        try {
            Container container = new Container("cycle.xml");
            container.getByClass("track.container.beans.Gear");
            System.out.println("- Cycle wasn't recognized.");
            Assert.assertEquals(1, 0);
        } catch (Exception exception) {
            System.out.println("+ Working with cycles is OK.");
        }
    }

    @Test
    public void initTest() throws Exception {
        try {
            Container container = new Container("config.xml");
            Car car = (Car) container.getByClass("track.container.beans.Car");
            Assert.assertFalse(car.getGear() == null);
            Assert.assertFalse(car.getEngine() == null);
            Assert.assertFalse(car.getBody() == null);
            System.out.println("+ Initializing is OK.");
        } catch (Exception exception) {
            System.out.println("- Wrong order of initializing.");
            Assert.assertEquals(1, 0);
        }
    }

    @Test
    public void valuesTest() throws Exception {
        try {
            Container container = new Container("config.xml");
            Car car = (Car) container.getByClass("track.container.beans.Car");
            Assert.assertTrue(car.getGear().getCount() == 6);
            Assert.assertTrue(car.getEngine().getPower() == 200);
            Assert.assertTrue(car.getBody().getWeight() == 1500);
            System.out.println("+ Properties have sought-for values.");
        } catch (Exception exception) {
            System.out.println("- Wrong values of properties.");
            Assert.assertEquals(1, 0);
        }
    }

    @Test
    public void wrongValuesTest() throws Exception {
        try {
            Container container = new Container("wrong_values.xml");
            Car car = (Car) container.getByClass("track.container.beans.Car");
            System.out.println("- Unsuitable values of properties weren't recognized.");
            Assert.assertEquals(1, 0);
        } catch (Exception exception) {
            System.out.println("+ Working with wrong values is OK.");
        }
    }

    @Test
    public void wrongQueryTest() throws Exception {
        Container container = new Container("config.xml");
        Car car1 = (Car) container.getByClass("track.container.beans.Far");
        Car car2 = (Car) container.getByName("carbean");
        if (car1 == null && car2 == null) {
            System.out.println("+ Working with wrong queries is OK.");
        } else {
            System.out.println("- Wrong query wasn't recognized.");
            Assert.assertEquals(1, 0);
        }
    }

    @Test
    public void duplicatesTest() throws Exception {
        Container container = new Container("config.xml");
        Car car = (Car) container.getByClass("track.container.beans.Car");
        car.getGear().setCount(3);
        Gear gear = (Gear) container.getByName("gearBean");
        if (gear.getCount() == 3) {
            System.out.println("+ Each bean is unique.");
        } else {
            System.out.println("- Beans are duplicating by multiple queries.");
            Assert.assertEquals(1, 0);
        }
    }
}
=======
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
        expectedEngine.setPower(200);

        expectedGear = new Gear();
        expectedGear.setCount(6);

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
>>>>>>> b8452fabb98625e85efa4e6a950c9beed23a5ec0
