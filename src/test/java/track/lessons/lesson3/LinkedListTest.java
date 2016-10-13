package track.lessons.lesson3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hellishnoob on 13.10.16.
 */
public class LinkedListTest {
    /**
     * @throws Exception Test the empty constructor and the constructor with a single argument by passing valid and invalid
     *                   initial capacity
     */
    @Test
    public void construct() throws Exception {
        List list = new LinkedList();
        assert (list.size() == 0);

        list = new DynamicList(1);
        assert (list.size() == 0);

        list = new DynamicList(0);
        assert (list.size() == 0);

        list = new DynamicList(-1);
        assert (list.size() == 0);
    }

    /**
     * @throws Exception Test add() method with a single item addition and with an addition of 100 items
     */
    @Test
    public void add() throws Exception {
        List list = new LinkedList();
        assert (list.size() == 0);

        list.add(5);
        assert (list.size() == 1);
        assert (list.get(0) == 5);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        assert (list.size() == 101);
        assert (list.get(100) == 99);
    }

    /**
     * @throws Exception Test remove() method with no items, a single item, valid and invalid item id's and by adding
     *                   and removing 100 items
     */
    @Test
    public void remove() throws Exception {
        List list = new LinkedList();
        assert (list.size() == 0);
        assert (list.remove(0) == List.INVALID_VALUE);
        assert (list.size() == 0);
        assert (list.remove(1) == List.INVALID_VALUE);
        assert (list.size() == 0);
        assert (list.remove(-1) == List.INVALID_VALUE);
        assert (list.size() == 0);

        list.add(1234);
        assert (list.remove(0) == 1234);
        assert (list.size() == 0);

        list.add(4321);
        assert (list.size() == 1);

        assert (list.remove(1) == List.INVALID_VALUE);
        assert (list.size() == 1);

        assert (list.remove(-1) == List.INVALID_VALUE);
        assert (list.size() == 1);
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (int i = 0; i < 100; i++) {
            assert (list.remove(0) == i);
        }

        assert (list.size() == 0);
    }

    /**
     * @throws Exception Test get() method by getting an item from an empty list, getting a single item, getting an item
     *                   with an invalid id, and by getting 100 items
     */
    @Test
    public void get() throws Exception {
        List list = new LinkedList();
        assert (list.get(0) == List.INVALID_VALUE);
        list.add(1234);
        assert (list.get(0) == 1234);
        assert (list.get(1) == List.INVALID_VALUE);
        assert (list.get(-1) == List.INVALID_VALUE);
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }


        for (int i = 99; i >= 0; i--) {
            assert (list.get(i) == i);
        }
    }

    /**
     * @throws Exception Tets size() method with no items added, a single item added, 1024 items added and all items removed
     */
    @Test
    public void size() throws Exception {
        List list = new LinkedList();
        assert (list.size() == 0);

        list.add(1234);
        assert (list.size() == 1);

        list.remove(0);
        assert (list.size() == 0);


        for (int i = 0; i < 1024; i++) {
            list.add(i);
        }

        assert (list.size() == 1024);

        for (int i = 0; i < 1024; i++) {
            list.remove(0);
        }

        assert (list.size() == 0);
    }

    /**
     * @throws Exception Text enqueue() method for adding an item to the queue by adding a single item or 1024 items and checking
     *                   the list size
     */
    @Test
    public void enqueue() throws Exception {
        Queue queue = new LinkedList();
        queue.enqueue(1234);
        assert (((List) queue).size() == 1);

        assert (((List) queue).get(0) == 1234);

        for (int i = 0; i < 1024; i++) {
            queue.enqueue(i);
        }

        assert (((List) queue).size() == 1025);
        assert (((List) queue).get(1024) == 1023);
    }

    /**
     * @throws Exception Test dequeue() method for getting the first added item to the queue and removing that item from the queue
     *                   by dequeuing an item from an empty queue, dequeuing a single item or 1024 items
     */
    @Test
    public void dequeu() throws Exception {
        Queue queue = new LinkedList();
        assert (queue.dequeu() == List.INVALID_VALUE);

        queue.enqueue(1234);
        assert (queue.dequeu() == 1234);
        assert (queue.dequeu() == List.INVALID_VALUE);

        for (int i = 0; i < 1024; i++) {
            queue.enqueue(i);
        }


        for (int i = 0; i < 1024; i++) {
            assert (queue.dequeu() == i);
        }

        assert (queue.dequeu() == List.INVALID_VALUE);
    }

    /**
     * @throws Exception Test push() method for adding an item to the stack by pushing a single item or 1024 items and checking
     *                   the list size
     */
    @Test
    public void push() throws Exception {
        Stack stack = new LinkedList();
        stack.push(54321);
        assert (((List) stack).size() == 1);
        assert (((List) stack).get(0) == 54321);

        for (int i = 0; i < 1024; i++) {
            stack.push(i);
        }

        assert (((List) stack).size() == 1025);
        assert (((List) stack).get(1024) == 1023);
    }

    /**
     * @throws Exception Test pop() method for getting the last added item to the stack and removing that item from the stack
     *                   by popping an item from an empty stack, popping a single item and popping 1024 items
     */
    @Test
    public void pop() throws Exception {
        Stack stack = new LinkedList();
        assert (stack.pop() == List.INVALID_VALUE);

        stack.push(54321);
        assert (stack.pop() == 54321);

        assert (((List) stack).size() == 0);
        assert (stack.pop() == List.INVALID_VALUE);

        for (int i = 0; i < 1024; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 1024; i++) {
            assert (stack.pop() == 1023 - i);
        }

        assert (stack.pop() == List.INVALID_VALUE);
    }
}