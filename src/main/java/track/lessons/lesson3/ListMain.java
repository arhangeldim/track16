package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {


        List list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(0);
        System.out.println(first);
        System.out.println(list.size());
        System.out.println(list.get(0));

    }
}
