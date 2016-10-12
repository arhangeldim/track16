package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        Queue queue = new LinkedList();
        for (int i = 0; i < 10; ++i) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; ++i) {
            System.out.println(queue.dequeue());
        }

        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(0);
        System.out.println(first);
        // System.out.println();
    }
}
