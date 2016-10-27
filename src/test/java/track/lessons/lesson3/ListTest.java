package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.*;

import java.lang.reflect.Field;

public class ListTest {


    @Test
    public void createDynamicListTest1() throws NoSuchFieldException, IllegalAccessException {
        DynamicList list = new DynamicList(15);
        Assert.assertEquals(list.size(), 0);
        Field privateField = list.getClass().getDeclaredField("array");
        privateField.setAccessible(true);
        int[] testArray = (int[]) privateField.get(list);
        Assert.assertEquals(testArray.length, 15);
    }

    @Test
    public void createDynamicLitTest2() throws NoSuchFieldException, IllegalAccessException {
        List list = new DynamicList();
        Assert.assertEquals(list.size(), 0);
        Field privateField = list.getClass().getDeclaredField("array");
        privateField.setAccessible(true);
        int[] testArray = (int[]) privateField.get(list);
        Assert.assertEquals(testArray.length, 10);
    }

    @Test
    public void addAndGetDynamicListTest() throws NoSuchFieldException, IllegalAccessException {
        List list = new DynamicList();
        Field privateField = list.getClass().getDeclaredField("array");
        privateField.setAccessible(true);
        for (int i = 0; i < 21; i++) {
            list.add(i);
            Assert.assertEquals(list.get(i), i);
            if (i == 9) {
                int[] testArray = (int[]) privateField.get(list);
                Assert.assertEquals(testArray.length, 10);
            }
            if (i == 10) {
                int[] testArray = (int[]) privateField.get(list);
                Assert.assertEquals(testArray.length, 20);
            }
            if (i == 20) {
                int[] testArray = (int[]) privateField.get(list);
                Assert.assertEquals(testArray.length, 40);
            }
        }
    }

    @Test
    public void removeDynamicListTest() {
        List list = new DynamicList();
        for (int i = 0; i < 21; i++) {
            list.add(i);
        }
        int delete = list.remove(19);
        Assert.assertEquals(list.size(), 20);
        Assert.assertEquals(list.get(19), 20);
        Assert.assertEquals(delete, 19);
        delete = list.remove(0);
        Assert.assertEquals(list.size(), 19);
        Assert.assertEquals(list.get(0), 1);
        Assert.assertEquals(delete, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionGetDynamicListTest1() {
        List list = new DynamicList();
        list.get(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionGetDynamicListTest2() {
        List list = new DynamicList();
        list.get(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionRemoveDynamicListTest1() {
        List list = new DynamicList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.remove(9);
        list.remove(9);
    }

    @Test
    public void createLinkedListTest() {
        List list = new LinkedList();
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void addAndGetLinkedListTest() {
        List list = new LinkedList();
        for (int i = 0; i < 21; i++) {
            list.add(i);
            Assert.assertEquals(list.get(i), i);
        }
    }

    @Test
    public void removeLinkedListTest() {
        List list = new LinkedList();
        for (int i = 0; i < 21; i++) {
            list.add(i);
        }
        int delete = list.remove(19);
        Assert.assertEquals(list.size(), 20);
        Assert.assertEquals(list.get(19), 20);
        Assert.assertEquals(delete, 19);
        delete = list.remove(0);
        Assert.assertEquals(list.size(), 19);
        Assert.assertEquals(list.get(0), 1);
        Assert.assertEquals(delete, 0);
        delete = list.remove(18);
        Assert.assertEquals(list.size(), 18);
        Assert.assertEquals(list.get(17), 18);
        Assert.assertEquals(delete, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionGetLinkedListTest1() {
        List list = new LinkedList();
        list.get(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionGetLinkedListTest2() {
        List list = new LinkedList();
        list.get(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionGetLinkedListTest3() {
        List list = new LinkedList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.remove(9);
        list.get(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionRemoveLinkedListTest1() {
        List list = new LinkedList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.remove(9);
        list.remove(9);
    }

    @Test
    public void queueTest1() {
        Queue queue = new LinkedList();
        for (int i = 0; i < 21; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 21; i++) {
            Assert.assertEquals(queue.dequeu(), i);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void queueTest2() {
        Queue queue = new LinkedList();
        for (int i = 0; i < 9; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            queue.dequeu();
        }
    }

    @Test
    public void stackTest() {
        Stack stack = new LinkedList();
        for (int i = 0; i < 21; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 21; i++) {
            Assert.assertEquals(stack.pop(), 20 - i);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void stackTest2() {
        Stack stack = new LinkedList();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 11; i++) {
            stack.pop();
        }
    }

}
