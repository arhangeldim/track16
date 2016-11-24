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
        Stack stack = (Stack) list;
        stack.push(1234);

        Queue queue = (Queue) list;
        queue.enqueue(23);
        System.out.print(queue.dequeue());

    }
}
