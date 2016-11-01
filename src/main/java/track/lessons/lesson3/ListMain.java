package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        LinkedList test = new LinkedList();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.size());
        test.remove(2);
        System.out.println(test.get(0));

        DynamicList one = new DynamicList();
        one.add(1);
        one.add(2);
        one.add(3);
        System.out.println(one.size());
        one.remove(1);
        System.out.println(one.get(0));
    }
}

