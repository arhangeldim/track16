package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hellishnoob on 13.10.16.
 */
public class DynamicListTest {
    /**
     * @throws Exception Test the empty constructor and the constructor with a single argument by passing valid and invalid
     *                   initial capacity
     */
    @Test
    public void construct() throws Exception {
        List list = new DynamicList();
        Assert.assertEquals(0, list.size());

        list = new DynamicList(1);
        Assert.assertEquals(0, list.size());

        list = new DynamicList(0);
        Assert.assertEquals(0, list.size());

        list = new DynamicList(-1);
        Assert.assertEquals(0, list.size());
    }

    /**
     * @throws Exception Test add() method with a single item addition and with an addition of 100 items
     */
    @Test
    public void add() throws Exception {
        List list = new DynamicList();
        Assert.assertEquals(0, list.size());

        list.add(5);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(5, list.get(0));

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        Assert.assertEquals(101, list.size());
        Assert.assertEquals(99, list.get(100));
    }

    /**
     * @throws Exception Test remove() method with no items, a single item, valid and invalid item id's and by adding
     *                   and removing 100 items
     */
    @Test
    public void remove() throws Exception {
        List list = new DynamicList();
        Assert.assertEquals(0, list.size());
        Assert.assertEquals(List.INVALID_VALUE, list.remove(0));
        Assert.assertEquals(0, list.size());
        Assert.assertEquals(List.INVALID_VALUE, list.remove(1));
        Assert.assertEquals(0, list.size());
        Assert.assertEquals(List.INVALID_VALUE, list.remove(-1));
        Assert.assertEquals(0, list.size());

        list.add(1234);
        Assert.assertEquals(1234, list.remove(0));
        Assert.assertEquals(0, list.size());

        list.add(4321);
        Assert.assertEquals(1, list.size());

        Assert.assertEquals(List.INVALID_VALUE, list.remove(1));
        Assert.assertEquals(1, list.size());

        Assert.assertEquals(List.INVALID_VALUE, list.remove(-1));
        Assert.assertEquals(1, list.size());
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        for (int i = 0; i < 100; i++) {
            Assert.assertEquals(i, list.remove(0));
        }

        Assert.assertEquals(0, list.size());
    }

    /**
     * @throws Exception Test get() method by getting an item from an empty list, getting a single item, getting an item
     *                   with an invalid id, and by getting 100 items
     */
    @Test
    public void get() throws Exception {
        List list = new DynamicList();
        Assert.assertEquals(List.INVALID_VALUE, list.get(0));
        list.add(1234);
        Assert.assertEquals(1234, list.get(0));
        Assert.assertEquals(List.INVALID_VALUE, list.get(1));
        Assert.assertEquals(List.INVALID_VALUE, list.get(-1));
        list.remove(0);

        for (int i = 0; i < 100; i++) {
            list.add(i);
        }


        for (int i = 99; i >= 0; i--) {
            Assert.assertEquals(i, list.get(i));
        }
    }

    /**
     * @throws Exception Tets size() method with no items added, a single item added, 1024 items added and all items removed
     */
    @Test
    public void size() throws Exception {
        List list = new DynamicList();
        Assert.assertEquals(0, list.size());

        list.add(1234);
        Assert.assertEquals(1, list.size());

        list.remove(0);
        Assert.assertEquals(0, list.size());


        for (int i = 0; i < 1024; i++) {
            list.add(i);
        }

        Assert.assertEquals(1024, list.size());

        for (int i = 0; i < 1024; i++) {
            list.remove(0);
        }

        Assert.assertEquals(0, list.size());
    }

}