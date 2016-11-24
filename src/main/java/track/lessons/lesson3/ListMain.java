package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {

        LinkedList list = new LinkedList();
        list.add(2);
        list.add(4);
        list.push(10);
        System.out.println(list.pop());
        System.out.println(list.dequeu());
        list.enqueue(8);
        System.out.println(list.size());
        DynamicList dynamicList = new DynamicList();
        dynamicList.add(10);
        dynamicList.add(8);
        dynamicList.add(10);
        System.out.println( dynamicList.remove(1) + " " + dynamicList.remove(1) + " " + dynamicList.size());


    }
}