package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import track.container.beans.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Container container = new Container("cycle.xml");
        System.out.println(container.getBeans());
        Gear gear = (Gear) container.getByClass("track.container.beans.Gear");
        System.out.println(gear.getCount());
        gear.setCount(5);
        System.out.println(gear.getCount());
    }

}
