package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {


        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        System.out.println(list.remove(0));
        System.out.println(list.get(1));
        DynamicList dynamicList = (DynamicList) list;
        dynamicList.push(45);
        System.out.println(dynamicList.pop());
        dynamicList.enqueue(23);
        System.out.println(dynamicList.dequeue());

        List list2 = new LinkedList();
        list2.add(1);
        list2.add(2);
        list2.add(10);
        System.out.println(list2.remove(1));
        System.out.println(list2.get(0));
        System.out.println(list2.size());
    }
}
