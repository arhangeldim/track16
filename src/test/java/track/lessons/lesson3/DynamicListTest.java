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
        List list = new DynamicList();
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
        List list = new DynamicList();
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
        List list = new DynamicList();
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
        List list = new DynamicList();
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

}