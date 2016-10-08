package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
/*
        List list = new DynamicList();
        list.printList();
        list.add(1);
        list.printList();
        list.add(2);
        list.printList();
        list.add(10);
        list.printList();
        int first = list.remove(0);
        System.out.println(first);
        first = list.remove(0);
        System.out.println(first);
        list.printList();
        first = list.remove(0);
        list.printList();
        list.remove(0);
        list.get(0);
*/


        List list = new LinkedList();
        list.printList();
        list.add(1);
        list.printList();
        list.add(3);
        list.printList();
        list.add(10);
        list.printList();
        list.remove(0);
        list.printList();
        list.remove(100);
        list.remove(2);
        list.remove(1);
        list.printList();
        System.out.println(list.get(0));
        System.out.println(list.remove(0));
        System.out.println(list.size());
        list.printList();


    }
}
