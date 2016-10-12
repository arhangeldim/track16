package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Андрей on 12.10.2016.
 */
public class ListTest {
    @Test
    public void correctTestLists() throws Exception {
        List[] lists = new List[2];
        lists[0] = new DynamicList();
        lists[1] = new LinkedList();
        for (int i = 0; i < 2; ++i) {
            lists[i].add(1);
            lists[i].add(2);
            lists[i].add(10);
            lists[i].add(5);
            lists[i].add(6);
            Assert.assertTrue(lists[i].remove(4) == 6);
            lists[i].add(1000);
            lists[i].add(2);
            lists[i].add(1);
            lists[i].add(2);
            lists[i].add(90);
            lists[i].add(100);
            lists[i].add(99);
            Assert.assertTrue(lists[i].remove(0) == 1);
            Assert.assertTrue(lists[i].remove(2) == 5);
            Assert.assertTrue(lists[i].get(6) == 90);
            Assert.assertTrue(lists[i].remove(1) == 10);
            Assert.assertTrue(lists[i].remove(7) == 99);
            Assert.assertTrue(lists[i].size() == 7);
        }
    }

    @Test
    public void correctTestStack() throws Exception {
        Stack stack = new LinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Assert.assertTrue(stack.pop() == 3);
        Assert.assertTrue(stack.pop() == 2);
        stack.push(4);
        Assert.assertTrue(stack.pop() == 4);
        Assert.assertTrue(stack.pop() == 1);
    }

    @Test
    public void correctTestQueue() throws Exception {
        Queue queue = new LinkedList();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Assert.assertTrue(queue.dequeue() == 1);
        Assert.assertTrue(queue.dequeue() == 2);
        queue.enqueue(4);
        Assert.assertTrue(queue.dequeue() == 3);
        Assert.assertTrue(queue.dequeue() == 4);

    }

    @Test(expected=Exception.class)
    public void exceptionTestLinkedList() throws Exception {
        List list = new LinkedList();
        list.add(10);
        list.get(1);
    }

    @Test(expected=Exception.class)
    public void exceptionTestDynamicList() throws Exception {
        List list = new DynamicList();
        list.add(5);
        list.remove(1);
    }

    @Test(expected=Exception.class)
    public void exceptionTestStack() throws Exception {
        Stack stack = new LinkedList();
        stack.push(1);
        stack.pop();
        stack.pop();
    }

    @Test(expected=Exception.class)
    public void exceptionTestQueue() throws Exception {
        Queue queue = new LinkedList();
        queue.enqueue(10);
        queue.dequeue();
        queue.dequeue();
    }


}
