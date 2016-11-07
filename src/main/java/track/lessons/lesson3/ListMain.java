package track.lessons.lesson3;

/**
 *Created by Konstantin on 13.10.2016.
 */
public class ListMain {

    public static void main(String[] args) throws WrongIndexException {


        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(2);
        System.out.println(first);
        System.out.println(list.get(1));

        Queue queue = new LinkedList();
        queue.enqueue(16);
        queue.enqueue(67);
        queue.enqueue(58);
        System.out.println(queue.dequeu());
        System.out.println(queue.dequeu());
        System.out.println(queue.dequeu());

        Stack stack = new LinkedList();
        stack.push(24);
        stack.push(89);
        stack.push(78);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
