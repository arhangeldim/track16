package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */

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
            try {
                Assert.assertEquals(list.get(i), i);
            } catch (WrongIndexException e) {
                System.out.println(e.getMessage());
            }
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
        int delete = 0;
        try {
            delete = list.remove(19);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(), 20);
        try {
            Assert.assertEquals(list.get(19), 20);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(delete, 19);
        try {
            delete = list.remove(0);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(), 19);
        try {
            Assert.assertEquals(list.get(0), 1);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(delete, 0);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionGetDynamicListTest1() throws WrongIndexException {
        List list = new DynamicList();
        list.get(100);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionGetDynamicListTest2() throws WrongIndexException {
        List list = new DynamicList();
        list.get(-1);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionRemoveDynamicListTest1() throws WrongIndexException {
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
            try {
                Assert.assertEquals(list.get(i), i);
            } catch (WrongIndexException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void removeLinkedListTest() {
        List list = new LinkedList();
        for (int i = 0; i < 21; i++) {
            list.add(i);
        }
        int delete = 0;
        try {
            delete = list.remove(19);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(), 20);
        try {
            Assert.assertEquals(list.get(19), 20);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(delete, 19);
        try {
            delete = list.remove(0);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(), 19);
        try {
            Assert.assertEquals(list.get(0), 1);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(delete, 0);
        try {
            delete = list.remove(18);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(list.size(), 18);
        try {
            Assert.assertEquals(list.get(17), 18);
        } catch (WrongIndexException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(delete, 20);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionGetLinkedListTest1() throws WrongIndexException {
        List list = new LinkedList();
        list.get(100);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionGetLinkedListTest2() throws WrongIndexException {
        List list = new LinkedList();
        list.get(-1);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionGetLinkedListTest3() throws WrongIndexException {
        List list = new LinkedList();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.remove(9);
        list.get(9);
    }

    @Test(expected = WrongIndexException.class)
    public void exceptionRemoveLinkedListTest1() throws WrongIndexException {
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
            try {
                Assert.assertEquals(queue.dequeu(), i);
            } catch (WrongIndexException e) {
                e.printStackTrace();
            }
        }
    }

    @Test(expected = WrongIndexException.class)
    public void queueTest2() throws WrongIndexException {
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
            try {
                Assert.assertEquals(stack.pop(), 20 - i);
            } catch (WrongIndexException e) {
                e.printStackTrace();
            }
        }
    }

    @Test(expected = WrongIndexException.class)
    public void stackTest2() throws WrongIndexException {
        Stack stack = new LinkedList();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 11; i++) {
            stack.pop();
        }
    }

}
