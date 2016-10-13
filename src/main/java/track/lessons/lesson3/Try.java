package track.lessons.lesson3;

import track.lessons.lesson3.DynamicList;
import track.lessons.lesson3.LinkedList;

/**
 * Created by iv on 13/10/2016.
 */
public class Try {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        System.out.println(list);
        System.out.println(list.check());
        list.add(1);
        System.out.println(list);
        System.out.println(list.check());
        list.add(6);
        System.out.println(list);
        System.out.println(list.check());
        try {
            System.out.println(list.get(1));
        } catch (Exception e) {

        }

    }
}
