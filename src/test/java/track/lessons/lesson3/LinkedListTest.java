package track.lessons.lesson3;

import org.junit.Before;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import static org.junit.Assert.*;

/**
 * Created by frystile on 13.10.16.
 */
public class LinkedListTest {
    LinkedList list;

    @Before
    public void setUp() throws Exception {
        list = new LinkedList();
    }

    @Test
    public void add() throws Exception {
        assert(list.size() == 0);
        list.add(1);
        assert(list.size() == 1);
        assert(list.get(0) == 1);
    }

    @Test
    public void remove() throws Exception {
        list.add(1);
        list.add(2);
        int temp = list.remove(1);
        assert(temp == 2);
        temp = list.remove(0);
        assert(temp == 1);
    }

    @Test
    public void get() throws Exception {
        list.add(1);
        list.add(2);
        assert(list.get(0) == 1);
        assert(list.get(1) == 2);
    }

    @Test
    public void size() throws Exception {
        assert(list.size() == 0);
        list.add(1);
        assert(list.size() == 1);
        list.remove(0);
        assert(list.size() == 0);
    }

    @Test
    public void enqueue() throws Exception {
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(10);
        assert(list.get(0) == 1);
        assert(list.get(1) == 2);
        assert(list.get(2) == 10);
    }

    @Test
    public void dequeu() throws Exception {
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(10);
        int temp = list.dequeu();
        assert(temp == 1);
        temp = list.dequeu();
        assert(temp == 2);
        temp = list.dequeu();
        assert(temp == 10);
    }

    @Test
    public void push() throws Exception {
        list.push(1);
        list.push(2);
        list.push(10);
        assert(list.get(0) == 1);
        assert(list.get(1) == 2);
        assert(list.get(2) == 10);
    }

    @Test
    public void pop() throws Exception {
        list.push(1);
        list.push(2);
        list.push(10);
        int temp = list.pop();
        assert(temp == 10);
        temp = list.pop();
        assert(temp == 2);
        temp = list.pop();
        assert(temp == 1);
    }
}