package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {


        DynamicList list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(0);
        System.out.println(first);
        System.out.println(list.get(list.size() - 1));

        LinkedList linkedList = new LinkedList();
        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(10);
        System.out.println(linkedList.get(2));
        first = linkedList.remove(0);
        System.out.println(first);
        System.out.println(linkedList.size());
        linkedList.push(0);
        System.out.println(linkedList.get(0));
        linkedList.pop();
        System.out.println(linkedList.get(0));
        linkedList.enqueue(11);
        System.out.println(linkedList.get(linkedList.size() - 1));
        linkedList.dequeue();
        System.out.println(linkedList.get(0));
    }
}
