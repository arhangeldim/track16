package track.lessons.lesson2;

import junit.framework.TestCase;
import track.lessons.lesson3.DynamicList;

/**
 * Created by pavel on 11.10.16.
 */
public class DynamicListTest extends TestCase {
    private DynamicList dynamicList;

    protected void setUp() throws Exception {
        int[] data = {1, 3, 2, 5};
        dynamicList = new DynamicList(data);
    }

    protected void tearDown() throws Exception {
    }

    public void testGet() {
        int expected = 3;
        int actual = dynamicList.get(1);
        assertEquals(expected, actual);
    }

    public void testSize() {
        int expected = 4;
        int actual = dynamicList.size();
        assertEquals(expected, actual);
    }

    public void testAdd() {
        int expected = 1;
        dynamicList.add(expected);
        int actual = dynamicList.get(dynamicList.size() - 1);
        assertEquals(dynamicList.size(), 5);
        assertEquals(expected, actual);
    }

    public void testRemove() {
        int expected = 5;
        int actual = dynamicList.remove(dynamicList.size() - 1);
        assertEquals(dynamicList.size(), 3);
        assertEquals(expected, actual);
    }
}
