package track.lessons.lesson2;


import junit.framework.TestCase;
import track.lessons.lesson3.LinkedList;

/**
 * Created by pavel on 11.10.16.
 */
public class LinkedListTest extends TestCase{

    LinkedList linkedList;

    protected void setUp() throws Exception {
        int[] data = {1, 3, 2, 5};
        linkedList = new LinkedList(data);
    }

    protected void tearDown() throws Exception {
    }

    public void testSize() {
        int expected = 4;
        int actual = linkedList.size();
        assertEquals(expected, actual);
    }

    public void testGet() {
        int expected = 3;
        int actual = linkedList.get(1);
        assertEquals(expected, actual);
    }

    public void testAdd() {
        int expected = 1;
        linkedList.add(expected);
        int actual = linkedList.get(linkedList.size() - 1);
        assertEquals(linkedList.size(), 5);
        assertEquals(expected, actual);
    }

    public void testRemove() {
        int expected = 3;
        int actual = linkedList.remove(1);
        linkedList.dump();
        assertEquals(linkedList.size(), 3);
        assertEquals(expected, actual);
    }

    public void testPop() {
        int expected = 5;
        int actual = linkedList.pop();
        assertEquals(linkedList.size(), 3);
        assertEquals(expected, actual);
    }
}
