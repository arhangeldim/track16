package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        List list = new LinkedList();
        list.add(1);
        System.out.println(list.remove(0));
        list.add(2);
        System.out.println(list.remove(0));
        System.out.println(list.size());
        list.add(2);
        list.add(3);
        System.out.println(list.size());
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        System.out.println(list.size());
        System.out.println(list.get(500));

        System.out.println(((Stack)list).pop());
        System.out.println(((Queue)list).dequeu());
    }
}
