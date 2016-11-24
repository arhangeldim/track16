package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        List list1 = new DynamicList();
        list1.add(1);
        list1.add(2);
        list1.add(10);
        int first = list1.remove(0);
        System.out.println(first);
        List list2 = new LinkedList(123);
        list2.add(54);
        first = list2.remove(0);
        System.out.println(first);
        System.out.println(list2.get(0));
    }
}
