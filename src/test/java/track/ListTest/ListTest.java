package track.ListTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import track.lessons.lesson3.List;
import track.lessons.lesson3.DynamicList;
import track.lessons.lesson3.LinkedList;
import track.lessons.lesson3.List;

/**
 * Created by iv on 13/10/2016.
 */
public class ListTest {

    @Test
    public void testLinked() throws Exception{
        LinkedList list = new LinkedList();
        testList(list);
    }

    @Test
    public void testDynamic() throws Exception{
        DynamicList list = new DynamicList();
        testList(list);
    }

    public void testList(List list) throws Exception{
        Assert.assertEquals(list.size(), 0);
        list.add(1);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0), 1);
        list.add(2);
        list.add(3);
        Assert.assertEquals(list.size(), 3);
        Assert.assertEquals(list.get(1), 2);
        Assert.assertEquals(list.get(2), 3);
        Assert.assertEquals(list.remove(1), 2);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.remove(1), 3);
        Assert.assertEquals(list.remove(0), 1);
        Assert.assertEquals(list.size(), 0);
    }

    @Test(expected = Exception.class)
    public void testIndexingLinked() throws Exception{
        LinkedList list = new LinkedList();
        list.get(1);
    }

    @Test(expected = Exception.class)
    public void testGettingLinked() throws Exception{
        LinkedList list = new LinkedList();
        list.add(1);
        list.get(1);
    }

    @Test(expected = Exception.class)
    public void testIndexingDynamic() throws Exception{
        DynamicList list = new DynamicList();
        list.get(1);
    }

    @Test(expected = Exception.class)
    public void testGettingDynamic() throws Exception{
        DynamicList list = new DynamicList();
        list.add(1);
        list.get(1);
    }

    @Test
    public void testStackLinked() throws Exception {
        LinkedList list = new LinkedList();
        testStack(list);
    }

    @Test
    public void testQueueLinked() throws Exception {
        LinkedList list = new LinkedList();
        testQueue(list);
    }

    @Test
    public void testStackDynamic() throws Exception {
        DynamicList list = new DynamicList();
        testStack(list);
    }

    @Test
    public void testQueueDynamic() throws Exception {
        DynamicList list = new DynamicList();
        testQueue(list);
    }

    public void testStack(List list) throws Exception {
        list.push(1);
        list.push(2);
        list.push(3);
        Assert.assertEquals(list.pop(), 3);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0), 1);
        Assert.assertEquals(list.get(1), 2);
        Assert.assertEquals(list.pop(), 2);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0), 1);
        Assert.assertEquals(list.pop(), 1);
        Assert.assertEquals(list.size(), 0);
    }

    public void testQueue(List list) throws Exception {
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(3);
        Assert.assertEquals(list.size(), 3);
        Assert.assertEquals(list.get(2), 1);
        Assert.assertEquals(list.get(1), 2);
        Assert.assertEquals(list.get(0), 3);

        Assert.assertEquals(list.dequeu(), 1);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(1), 2);
        Assert.assertEquals(list.get(0), 3);

        Assert.assertEquals(list.dequeu(), 2);
        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0), 3);

        Assert.assertEquals(list.dequeu(), 3);
        Assert.assertEquals(list.size(), 0);
    }
}


// int arr = int[5];
// char arr2 = char[5];
//
// for ():