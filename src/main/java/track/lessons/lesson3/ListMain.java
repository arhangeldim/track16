package track.lessons.lesson3;

/**
 *
 */
public class ListMain {
    public static void main(String[] args) throws Exception {
        List dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(2);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.get(0));
        dynamicList.add(4);
        dynamicList.add(3);
        System.out.println(dynamicList.size());

        List linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        System.out.println(linkedList.remove(0));
        System.out.println(linkedList.get(0));
        linkedList.add(4);
        linkedList.add(3);
        System.out.println(linkedList.size());

        Stack stack = new LinkedList();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        stack.push(3);
        System.out.println(stack.pop());

        Queue queue = new LinkedList();
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println(queue.dequeue());
        queue.enqueue(3);
        System.out.println(queue.dequeue());
    }
}