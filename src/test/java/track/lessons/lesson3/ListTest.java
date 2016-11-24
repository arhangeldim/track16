package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by egiby on 12.10.16.
 */
public class ListTest {
    private static final int TEST_SIZE = 1000;
    private static final int MAX_EVENT = 3;
    private static Random gen = new Random(50100);

    private void listTest(List list) {
        java.util.List<Integer> good_list = new ArrayList<>();

        for (int i = 0; i < TEST_SIZE; ++i) {
            list.add(i);
            good_list.add(i);
        }

        for (int i = 0; i < TEST_SIZE; ++i) {
            Assert.assertTrue(list.size() == good_list.size());

            switch (gen.nextInt(MAX_EVENT)) {
                // add
                case 0: {
                    int num = gen.nextInt();
                    good_list.add(num);
                    list.add(num);
                    Assert.assertEquals(list.get(list.size() - 1), (int)good_list.get(list.size() - 1));
                    break;
                }
                // remove
                case 1: {
                    int idx = gen.nextInt(list.size());
                    Assert.assertEquals(list.remove(idx), (int)good_list.remove(idx));
                    break;
                }
                // get
                case 2: {
                    int idx = gen.nextInt(list.size());
                    Assert.assertEquals(list.get(idx), (int)good_list.get(idx));
                    break;
                }
            }
        }
    }

    @Test
    public void linkedListTest() {
        List list = new LinkedList();
        listTest(list);
    }

    @Test
    public void dynamicListTest() {
        List list = new DynamicList();
        listTest(list);
    }
}
