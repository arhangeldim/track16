package track.lessons.lesson3;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by altair on 12.10.16.
 */
public class ListTest {

    static public void test(List list) throws Exception {
        Throwable e = null;

        try {
            list.get(0);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);

        e = null;
        try {
            list.remove(0);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);

        list.add(1);
        Assert.assertEquals(list.remove(0), 1);
        Assert.assertEquals(list.size(), 0);
        e = null;
        try {
            list.get(0);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);

        for(int i = 0; i < 10; i++) {
            list.add(i);
        }
        Assert.assertEquals(list.size(), 10);
        for(int i = 0; i < 10; i++) {
            Assert.assertEquals(list.get(i), i);
        }
        for(int i = 9; i > 0; i--) {
            Assert.assertEquals(list.get(i), i);
        }
        Assert.assertEquals(list.size(), 10);

        Assert.assertEquals(list.remove(0), 0);
        Assert.assertEquals(list.size(), 9);

        Assert.assertEquals(list.remove(5), 6);
        Assert.assertEquals(list.size(), 8);

        list.add(10);
        Assert.assertEquals(list.size(), 9);

        e = null;
        try {
            list.get(100);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);

        e = null;
        try {
            list.remove(100);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);

        e = null;
        try {
            list.get(-1);
        } catch (Exception ex) {
            e = ex;
        }
        Assert.assertTrue(e instanceof IndexOutOfBoundsException);
    }

    @Test
    public void dynamicTest() throws Exception {
        test(new DynamicList());
    }

    @Test
    public void linkedTest() throws Exception {
        test(new LinkedList());
    }

    @Test
    public void allTest() throws Exception {
        dynamicTest();
        linkedTest();
    }

}
