package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamicListTest {

    public static final int countOfAttempt = 4242;

    @Test
    public void add() throws Exception {
        DynamicList dt = new DynamicList();

        for (int i = 0; i < countOfAttempt; ++i) {
            dt.add(i);
            Assert.assertEquals(i, dt.size() - 1);
            Assert.assertEquals(i, dt.get(i));
        }

        for (int i = 0; i < countOfAttempt; ++i) {
            dt.add(countOfAttempt - i);
            Assert.assertEquals(i + countOfAttempt, dt.size() - 1);
            Assert.assertEquals(countOfAttempt - i, dt.get(i + countOfAttempt));
        }
    }

    @Test
    public void remove() throws Exception {
        DynamicList dt = new DynamicList();

        for (int i = 0; i < countOfAttempt; ++i) {
            dt.add(i);
            Assert.assertEquals(i, dt.size() - 1);
            Assert.assertEquals(i, dt.get(i));
        }

        for (int i = 0; i < countOfAttempt / 10; ++i) {
            int value = dt.remove(0);
            Assert.assertEquals(i, value);
            Assert.assertEquals(countOfAttempt - 2*i - 1, dt.size());
            value = dt.remove(dt.size() - 1);
            Assert.assertEquals(countOfAttempt - i - 1, value);
            Assert.assertEquals(countOfAttempt - 2*i - 2, dt.size());
        }
        int size = dt.size();
        Assert.assertEquals(dt.remove(-1), 0);
        Assert.assertEquals(size, dt.size());
        size = dt.size();
        Assert.assertEquals(dt.remove(countOfAttempt), 0);
        Assert.assertEquals(size, dt.size());

    }

    @Test
    public void get() throws Exception {

        DynamicList dt = new DynamicList();

        for (int i = 0; i < countOfAttempt; ++i) {
            dt.add(i);
            Assert.assertEquals(i, dt.size() - 1);
            Assert.assertEquals(i, dt.get(i));
        }

        for (int i = 1; i < countOfAttempt / 10; ++i) {
            int value = dt.get(i * 5);
            Assert.assertEquals(value, i * 5);
            Assert.assertEquals(countOfAttempt, dt.size());
        }

        int size = dt.size();
        Assert.assertEquals(dt.get(-1), 0);
        Assert.assertEquals(size, dt.size());
        size = dt.size();
        Assert.assertEquals(dt.get(countOfAttempt), 0);
        Assert.assertEquals(size, dt.size());

    }

}