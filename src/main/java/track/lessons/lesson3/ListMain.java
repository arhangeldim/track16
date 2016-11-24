package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {

        System.out.println("DynamicList tests.");
        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        System.out.println(list.size());
        int first = list.remove(0);
        System.out.println("Removed item = " + first);
        System.out.println(list.size());
        System.out.println(list.get(1));

        System.out.println("LinkedList tests.");
        list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(10);
        System.out.println(list.size());
        first = list.remove(0);
        System.out.println("Removed item = " + first);
        System.out.println(list.size());
        System.out.println(list.get(1));

        System.out.println("Stack tests.");
        Stack stack = new LinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        System.out.println("Queue tests.");
        Queue queue = new LinkedList();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        System.out.println(queue.dequeu());
        System.out.println(queue.dequeu());
        System.out.println(queue.dequeu());
        System.out.println(queue.dequeu());
    }
}
