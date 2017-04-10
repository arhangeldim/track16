package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {

    LinkedList list;

    @Before
    public void init() {
        list = new LinkedList();
        for (int i = 0; i < 16; i++) {
            list.add(i);
        }
    }

    @Test
    public void testSize() {
        Assert.assertEquals(16, list.size());
    }

    @Test
    public void testGetFirst() {
        Assert.assertEquals(0, list.get(0));
    }

    @Test
    public void testGetLast() {
        Assert.assertEquals(15, list.get(list.size() - 1));
    }

    @Test
    public void testGetInside() {
        Assert.assertEquals(3, list.get(3));
    }


    @Test
    public void testAdd() {
        int oldSize = list.size();
        list.add(15);
        Assert.assertEquals(oldSize + 1, list.size());
        Assert.assertEquals(15, list.get(15));
    }

    @Test
    public void testRemove() {
        int oldSize = list.size();
        int elem = list.remove(7);
        Assert.assertEquals(oldSize - 1, list.size());
        Assert.assertEquals(elem, 7);
        Assert.assertEquals(8, list.get(7));
    }
}
