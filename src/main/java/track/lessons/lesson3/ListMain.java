package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        Queue queue = new LinkedList();
        Stack stack = new LinkedList();
        for (int i = 0; i < 10; ++i) {
            queue.enqueue(i);
            stack.push(i);
        }

        for (int i = 0; i < 10; ++i) {
            System.out.print("queue: ");
            System.out.println(queue.dequeue());
            System.out.print("stack: ");
            System.out.println(stack.pop());
        }

        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(0);
        System.out.println(first);
    }
}
