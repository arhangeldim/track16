package track.lessons.lesson3;

/**
 *
 */
public class ListMain {
    private static final int[] testArray = {1, 2, 3, 40, 500, 666, 777};

    public static void main(String[] args) {
        showDynamicListUsage();
        showLinkedListUsage();
        showStackUsage();
        showQueueUsage();
    }

    private static void showDynamicListUsage() {
        List dynList = new DynamicList();

        /* Add all items from testArray */
        for (int item : testArray) {
            dynList.add(item);
        }

        System.out.println("There are " + dynList.size() + " items in dynList");
        System.out.println("Printing all items from dynList:");
        for (int i = 0; i < dynList.size(); i++) {
            System.out.println(dynList.get(i));
        }

        /* Remove a couple of items from dynList */
        System.out.println("Removed an item that was equal to " + dynList.remove(2));
        System.out.println("Removed an item that was equal to " + dynList.remove(4));


        System.out.println("Printing all items from dynList:");
        for (int i = 0; i < dynList.size(); i++) {
            System.out.println(dynList.get(i));
        }

        /* Remove all items from dynList */
        for (int i = 0; i < dynList.size(); i++) {
            dynList.remove(0);
        }

        /* dynList.get() returns List.INVALID_VALUE when trying to get a non-existing item */
        if (dynList.get(0) == List.INVALID_VALUE) {
            System.out.println("Tried to get an item from the empty list, got List.INVALID_VALUE as a return value");
        }
    }

    private static void showLinkedListUsage() {
        List linkedList = new LinkedList();

        /* Add all items from testArray */
        for (int item : testArray) {
            linkedList.add(item);
        }

        System.out.println("There are " + linkedList.size() + " items in linkedList");
        System.out.println("Printing all items from linkedList:");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }

        /* Remove a couple of items from dynList */
        System.out.println("Removed an item that was equal to " + linkedList.remove(2));
        System.out.println("Removed an item that was equal to " + linkedList.remove(4));


        System.out.println("Printing all items from linkedList:");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }

        /* Remove all items from dynList */
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.remove(0);
        }

        /* linkedList.get() returns List.INVALID_VALUE when trying to get a non-existing item */
        if (linkedList.get(0) == List.INVALID_VALUE) {
            System.out.println("Tried to get an item from the empty list, got List.INVALID_VALUE as a return value");
        }
    }

    private static void showQueueUsage() {
        Queue queue = new LinkedList();

        /* Add a single item to the queue */
        queue.enqueue(1234);

        /* Dequeue the item that was recently added to the queue */
        System.out.println("Added 1234 to the queue and dequeued " + queue.dequeu() + " from it");

        /* Add all items from testArray to the queue*/
        for (int item : testArray) {
            queue.enqueue(item);
        }

        /* Dequeue the first added item to the queue */
        System.out.println("Added all items from testArray to the queue and" +
                "dequeued " + queue.dequeu() + " from the bottom of the queue");
    }

    private static void showStackUsage() {
        Stack stack = new LinkedList();

        /* Push a single item to the top of the stack */
        stack.push(1234);

        /* Pop the item that was recently pushed */
        System.out.println("Pushed 1234 to the stack and popped " + stack.pop() + " from it");

        /* Push all items from testArray */
        for (int item : testArray) {
            stack.push(item);
        }

        /* Pop the last item */
        System.out.println("Added all items from testArray and popped " + stack.pop() + " from the top of the stack");
    }
}
