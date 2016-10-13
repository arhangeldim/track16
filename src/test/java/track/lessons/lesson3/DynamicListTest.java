package track.lessons.lesson3;

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
        assertEquals(0, list.size());

        list = new DynamicList(1);
        assertEquals(0, list.size());

        list = new DynamicList(0);
        assertEquals(0, list.size());

        list = new DynamicList(-1);
        assertEquals(0, list.size());
    }

    /**
     * @throws Exception Test add() method with a single item addition and with an addition of 100 items
     */
    @Test
    public void add() throws Exception {
        List list = new DynamicList();
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
        List list = new DynamicList();
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
        List list = new DynamicList();
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
        List list = new DynamicList();
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

}