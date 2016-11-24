package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by mannimarco on 11/10/2016.
 */
public class LinkedListTest {

    private static LinkedList linkedList;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void remove() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        int elem = linkedList.remove(4);

        Assert.assertEquals(30, elem);
    }

    @Test
    public void removeFromEmpty() throws Exception {
        linkedList = new LinkedList();

        thrown.expect(IndexOutOfBoundsException.class);

        linkedList.remove(-1);
    }

    @Test
    public void getNode() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        Node node = linkedList.getNode(1);

        Assert.assertTrue((node.getPrev().getValue() == 1) & (node.getNext().getValue() == 10));
    }


    @Test
    public void add() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        linkedList.add(40);

        Assert.assertEquals(40, linkedList.get(linkedList.size() - 1));
    }


    @Test
    public void get() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        int elem = linkedList.get(0);

        Assert.assertEquals(1, elem);
    }

    @Test
    public void getIncorrectIndex() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);

        thrown.expect(IndexOutOfBoundsException.class);

        linkedList.get(5);
    }

    @Test
    public void size() throws Exception {
        linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(5);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);

        int size = linkedList.size();
        Assert.assertEquals(5, size);
    }

}