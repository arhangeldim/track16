package track.lessons.lesson3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hellishnoob on 13.10.16.
 */
public class LinkedListTest {
    /**
     * @throws Exception Test LinkedList constructor
     */
    @Test
    public void construct() throws Exception {
        List list = new LinkedList();
        assertEquals(0, list.size());
    }

    /**
     * @throws Exception Test add() method with a single item addition and with an addition of 100 items
     */
    @Test
    public void add() throws Exception {
        List list = new LinkedList();
        assertEquals(0, list.size());

        list.add(5);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0));

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        assertEquals(101, list.size());
        assertEquals(99, list.get(100));
    }

    /**
     * @throws Exception Test remove() method with no items, a single item, valid and invalid item id's and by adding
     *                   and removing 100 items
     */
    @Test
    public void remove() throws Exception {
        List list = new LinkedList();
        assertEquals(0, list.size());
        assertEquals(List.INVALID_VALUE, list.remove(0));
        assertEquals(0, list.size());
        assertEquals(List.INVALID_VALUE, list.remove(1));
        assertEquals(0, list.size());
        assertEquals(List.INVALID_VALUE, list.remove(-1));
        assertEquals(0, list.size());

        list.add(1234);
        assertEquals(1234, list.remove(0));
        assertEquals(0, list.size());

        list.add(4321);
        assertEquals(1, list.size());

        assertEquals(List.INVALID_VALUE, list.remove(1));
        assertEquals(1, list.size());

        assertEquals(List.INVALID_VALUE, list.remove(-1));
        assertEquals(1, list.size());
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(i, list.remove(0));
        }

        assertEquals(0, list.size());
    }

    /**
     * @throws Exception Test get() method by getting an item from an empty list, getting a single item, getting an item
     *                   with an invalid id, and by getting 100 items
     */
    @Test
    public void get() throws Exception {
        List list = new LinkedList();
        assertEquals(List.INVALID_VALUE, list.get(0));
        list.add(1234);
        assertEquals(1234, list.get(0));
        assertEquals(List.INVALID_VALUE, list.get(1));
        assertEquals(List.INVALID_VALUE, list.get(-1));
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }


        for (int i = 99; i >= 0; i--) {
            assertEquals(i, list.get(i));
        }
    }

    /**
     * @throws Exception Tets size() method with no items added, a single item added, 1024 items added and all items removed
     */
    @Test
    public void size() throws Exception {
        List list = new LinkedList();
        assertEquals(0, list.size());

        list.add(1234);
        assertEquals(1, list.size());

        list.remove(0);
        assertEquals(0, list.size());


        for (int i = 0; i < 1024; i++) {
            list.add(i);
        }

        assertEquals(1024, list.size());

        for (int i = 0; i < 1024; i++) {
            list.remove(0);
        }

        assertEquals(0, list.size());
    }

    /**
     * @throws Exception Text enqueue() method for adding an item to the queue by adding a single item or 1024 items and checking
     *                   the list size
     */
    @Test
    public void enqueue() throws Exception {
        Queue queue = new LinkedList();
        queue.enqueue(1234);
        assertEquals(1, ((List) queue).size());

        assertEquals(1234, ((List) queue).get(0));

        for (int i = 0; i < 1024; i++) {
            queue.enqueue(i);
        }

        assertEquals(1025, ((List) queue).size());
        assertEquals(1023, ((List) queue).get(1024));
    }

    /**
     * @throws Exception Test dequeue() method for getting the first added item to the queue and removing that item from the queue
     *                   by dequeuing an item from an empty queue, dequeuing a single item or 1024 items
     */
    @Test
    public void dequeu() throws Exception {
        Queue queue = new LinkedList();
        assertEquals(List.INVALID_VALUE, queue.dequeu());

        queue.enqueue(1234);
        assertEquals(1234, queue.dequeu());
        assertEquals(List.INVALID_VALUE, queue.dequeu());

        for (int i = 0; i < 1024; i++) {
            queue.enqueue(i);
        }


        for (int i = 0; i < 1024; i++) {
            assertEquals(i, queue.dequeu());
        }

        assertEquals(List.INVALID_VALUE, queue.dequeu());
    }

    /**
     * @throws Exception Test push() method for adding an item to the stack by pushing a single item or 1024 items and checking
     *                   the list size
     */
    @Test
    public void push() throws Exception {
        Stack stack = new LinkedList();
        stack.push(54321);
        assertEquals(1, ((List) stack).size());
        assertEquals(54321, ((List) stack).get(0));

        for (int i = 0; i < 1024; i++) {
            stack.push(i);
        }

        assertEquals(1025, ((List) stack).size());
        assertEquals(1023, ((List) stack).get(1024));
    }

    /**
     * @throws Exception Test pop() method for getting the last added item to the stack and removing that item from the stack
     *                   by popping an item from an empty stack, popping a single item and popping 1024 items
     */
    @Test
    public void pop() throws Exception {
        Stack stack = new LinkedList();
        assertEquals(List.INVALID_VALUE, stack.pop());

        stack.push(54321);
        assertEquals(54321, stack.pop());

        assertEquals(0, ((List) stack).size());
        assertEquals(List.INVALID_VALUE, stack.pop());

        for (int i = 0; i < 1024; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 1024; i++) {
            assertEquals(1023 - i, stack.pop());
        }

        assertEquals(List.INVALID_VALUE, stack.pop());
    }
}