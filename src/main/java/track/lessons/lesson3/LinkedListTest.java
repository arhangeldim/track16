package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedListTest {

    public static final int countOfAttempt = 4242;

    @Test
    public void add() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.add(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.add(countOfAttempt - i);
            Assert.assertEquals(i + countOfAttempt, lt.size() - 1);
            Assert.assertEquals(countOfAttempt - i, lt.get(i + countOfAttempt));
        }
    }

    @Test
    public void remove() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.add(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }

        for (int i = 0; i < countOfAttempt / 10; ++i) {
            int value = lt.remove(0);
            Assert.assertEquals(i, value);
            Assert.assertEquals(countOfAttempt - 2 * i - 1, lt.size());
            value = lt.remove(lt.size() - 1);
            Assert.assertEquals(countOfAttempt - i - 1, value);
            Assert.assertEquals(countOfAttempt - 2 * i - 2, lt.size());
        }
        int size = lt.size();
        Assert.assertEquals(lt.remove(-1), 0);
        Assert.assertEquals(size, lt.size());
        size = lt.size();
        Assert.assertEquals(lt.remove(countOfAttempt), 0);
        Assert.assertEquals(size, lt.size());
    }

    @Test
    public void get() throws Exception {

        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.add(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }

        for (int i = 1; i < countOfAttempt / 10; ++i) {
            int value = lt.get(i * 5);
            Assert.assertEquals(value, i * 5);
            Assert.assertEquals(countOfAttempt, lt.size());
        }

        int size = lt.size();
        Assert.assertEquals(lt.get(-1), 0);
        Assert.assertEquals(size, lt.size());
        size = lt.size();
        Assert.assertEquals(lt.get(countOfAttempt), 0);
        Assert.assertEquals(size, lt.size());
    }

    @Test
    public void getNode() throws Exception {
        // Проверяется в get()
    }

    @Test
    public void enqueue() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.enqueue(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }
    }

    @Test
    public void dequeue() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.enqueue(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }

        for (int i = 0; i < countOfAttempt; ++i) {
            int value = lt.dequeue();
            Assert.assertEquals(value, i);
            Assert.assertEquals(lt.size(), countOfAttempt - 1 - i);
        }

        int size = lt.size();
        Assert.assertEquals(lt.dequeue(), 0);
        Assert.assertEquals(size, lt.size());
    }

    @Test
    public void push() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.push(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }
    }

    @Test
    public void pop() throws Exception {
        LinkedList lt = new LinkedList();

        for (int i = 0; i < countOfAttempt; ++i) {
            lt.push(i);
            Assert.assertEquals(i, lt.size() - 1);
            Assert.assertEquals(i, lt.get(i));
        }

        for (int i = 0; i < countOfAttempt; ++i) {
            int value = lt.pop();
            Assert.assertEquals(value, countOfAttempt - 1 - i);
            Assert.assertEquals(lt.size(), countOfAttempt - 1 - i);
        }

        int size = lt.size();
        Assert.assertEquals(lt.pop(), 0);
        Assert.assertEquals(size, lt.size());
    }

}