package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {


        List list = new DynamicList(5);
        list.add(1);
        list.add(2);
        list.add(10);
        list.print();
        int first = list.remove(0);
        int second = list.get(1);
        System.out.println(list.size());
        list.print();
        list.remove(1);
        list.remove(0);
        list.print();
        for(int i = 0; i < 25; i++)
        {
            list.add(i);
        }
        list.print();

        List list1 = new LinkedList();
        list1.add(5);
        list1.add(8);
        list1.add(13);
        System.out.println(list1.size());
        list1.print();
        System.out.println(list1.get(2));
        list1.remove(1);
        list1.print();
        list1.remove(1);
        list1.print();
        list1.remove(0);
        list1.print();
        System.out.println(list1.size());

        LinkedList list2 = new LinkedList();
        list2.push(5);
        list2.push(4);
        System.out.println(list2.pop());
        list2.print();
        System.out.println("\n");
        LinkedList list3 = new LinkedList();
        list3.enqueue(67);
        list3.enqueue(23);
        list3.print();
        System.out.println(list3.dequeue());
        System.out.println("\n");
        list3.print();
        /*list1.add(13);
        System.out.println(list1.size());

        System.out.println(list1.get(0));*/
    }
}
