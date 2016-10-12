package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import track.container.beans.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container("config.xml");
        System.out.println(container.getBeans());
        //	Class c = container.getBeans().getClass();
        Car car = (Car) container.getByClass("track.container.beans.Car");
        System.out.println(car.getGear().getCount());
        car.getGear().setCount(5);
        System.out.println(car.getGear().getCount());
    }

}
