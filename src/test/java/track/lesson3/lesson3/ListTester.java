package track.lesson3.lesson3;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import track.lessons.lesson3.DynamicList;
import track.lessons.lesson3.LinkedList;


/**
 * Created by mif-a on 08.10.2016.
 */
public class ListTester {
    public static ArrayList<Integer> dynamicList1;
    public static DynamicList dynamicList2;
    public static java.util.LinkedList<Integer> linkedList1;
    public static LinkedList linkedList2;
    @BeforeClass
    public static void init() {
        dynamicList1 = new ArrayList<Integer>();
        dynamicList2 = new DynamicList();
        linkedList1 = new java.util.LinkedList<Integer>();
        linkedList2 = new LinkedList();
        Random random = new Random();
        int size = 10;
        for (int i = 0; i < size; ++i) {
            int value = random.nextInt();
            dynamicList1.add(value);
            dynamicList2.add(value);
            linkedList1.add(value);
            linkedList2.add(value);
        }
    }

    @Test
    public void listTester() throws Exception {
        for (int i = 0; i < linkedList1.size(); ++i) {
            System.out.println(linkedList1.get(i) + " " + linkedList2.get(i));
            Assert.assertTrue(linkedList1.get(i) == linkedList2.get(i));
            Assert.assertTrue(dynamicList1.get(i) == dynamicList2.get(i));
        }
        Assert.assertTrue(linkedList1.size() == linkedList2.size());
        Assert.assertTrue(dynamicList1.size() == dynamicList2.size());
        int idx = new Random().nextInt(linkedList1.size());
        System.out.println(idx);
        Assert.assertTrue(dynamicList1.remove(idx) == dynamicList2.remove(idx));
        Assert.assertTrue(linkedList1.remove(idx) == linkedList2.remove(idx));
        Assert.assertTrue(linkedList1.remove(0) == linkedList2.remove(0));
        for (int i = 0; i < linkedList1.size(); ++i) {
            System.out.println(linkedList1.get(i) + " " + linkedList2.get(i));
            Assert.assertTrue(linkedList1.get(i) == linkedList2.get(i));
            Assert.assertTrue(dynamicList1.get(i) == dynamicList2.get(i));
        }

    }
}
