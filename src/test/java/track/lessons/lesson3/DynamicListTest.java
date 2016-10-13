package track.lessons.lesson3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frystile on 13.10.16.
 */
public class DynamicListTest {
    DynamicList list;
    @Before
    public void setUp() throws Exception {
        list = new DynamicList();
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

}