package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by oleg on 10.10.16.
 */
public class ListTest {
    public void generalTest(List list) {

        Assert.assertEquals(0, list.size());
        list.add(1);
        list.add(2);
        list.add(3);
        Assert.assertEquals(3, list.get(list.size() - 1));
        Assert.assertEquals(3, list.remove(list.size() - 1));
        Assert.assertEquals(1, list.remove(0));
        Assert.assertEquals(2, list.remove(0));
        Assert.assertEquals(0, list.remove(0));
    }
    @Test
    public void testDynamicList() {
        DynamicList dynamicList = new DynamicList();
        generalTest(dynamicList);
    }

    @Test
    public void testLinkedList() {
        LinkedList linkedList = new LinkedList();
        generalTest(linkedList);
    }

    @Test
    public void testStack() {
        Stack stack = new DynamicList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assert.assertEquals(3, stack.pop());
        Assert.assertEquals(2, stack.pop());
        Assert.assertEquals(1, stack.pop());
        Assert.assertEquals(0, stack.pop());
    }

    @Test
    public void testQueue() {
        Queue queue = new DynamicList();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Assert.assertEquals(1, queue.dequeu());
        Assert.assertEquals(2, queue.dequeu());
        Assert.assertEquals(3, queue.dequeu());
        Assert.assertEquals(0, queue.dequeu());
    }

}
