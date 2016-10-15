package track.lessons.lesson3;
import org.junit.Test;
import java.util.*;
import java.util.List;
import static org.junit.Assert.*;
/**
 * Created by zerts on 14.10.16.
 */

public class DynamicListTest {
    @Test
    public void add() throws Exception {
        List<Integer> correct = new ArrayList<Integer>();
        DynamicList l = new DynamicList();
        for (int i = 0; i < 10; i++) {
            l.add(i);
            correct.add(i);
        }
        for (int j = 9; j >= 0; j--) {
            l.remove(j);
            correct.remove(j);
            for (int i = 0; i < 10; i++) {
                l.add(i);
                correct.add(i);
            }
            for (int i = 0; i < l.size(); i++) {
                assertEquals(l.size(), correct.size());
                assertEquals(new Integer(l.get(i)), correct.get(i));
            }
        }
    }
    @Test
    public void remove() throws Exception {
    }
    @Test
    public void get() throws Exception {
    }
    @Test
    public void size() throws Exception {
    }
}