package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by mannimarco on 11/10/2016.
 */
public class DynamicListTest {

    private static DynamicList dynamicList;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void remove() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);

        int elem = dynamicList.remove(2);

        Assert.assertEquals(10, elem);
    }

    @Test
    public void removeFromEmpty() throws IndexOutOfBoundsException {
        dynamicList = new DynamicList();

        thrown.expect(IndexOutOfBoundsException.class);

        int elem = dynamicList.remove(1);

    }

    @Test
    public void add() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);

        dynamicList.add(20);

        Assert.assertEquals(20, dynamicList.get(dynamicList.size() - 1));
    }

    @Test
    public void get() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);

        int elem = dynamicList.get(2);

        Assert.assertEquals(elem, 10);
    }

    @Test
    public void getIncorrectIndex() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(5);

        thrown.expect(IndexOutOfBoundsException.class);

        dynamicList.get(2);
    }

    @Test
    public void size() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);
        dynamicList.add(20);
        dynamicList.add(30);

        int size = dynamicList.size();

        Assert.assertEquals(5, size);
    }


    @Test
    public void enqueue() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);
        dynamicList.add(20);
        dynamicList.add(30);

        dynamicList.enqueue(100);

        Assert.assertEquals(100, dynamicList.get(dynamicList.size() - 1));
    }

    @Test
    public void dequeue() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);
        dynamicList.add(20);
        dynamicList.add(30);

        int elem = dynamicList.dequeue();

        Assert.assertEquals(1, elem);
    }

    @Test
    public void dequeueFromEmpty() throws Exception {
        dynamicList = new DynamicList();

        thrown.expect(IndexOutOfBoundsException.class);

        dynamicList.dequeue();
    }

    @Test
    public void push() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);
        dynamicList.add(20);
        dynamicList.add(30);

        dynamicList.push(20);

        Assert.assertEquals(20, dynamicList.pop());
    }

    @Test
    public void pop() throws Exception {
        dynamicList = new DynamicList();
        dynamicList.add(1);
        dynamicList.add(5);
        dynamicList.add(10);
        dynamicList.add(20);
        dynamicList.add(30);

        dynamicList.push(25);
        int elem = dynamicList.pop();
        Assert.assertEquals(25, elem);
    }

    @Test
    public void popFromEmpty() throws Exception {
        dynamicList = new DynamicList();

        thrown.expect(IndexOutOfBoundsException.class);

        dynamicList.pop();
    }
}