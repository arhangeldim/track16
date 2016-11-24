package track.lessons.lesson3;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by leonshting on 09.10.16.
 */
public class LinkedListTest {

    static String[] data;
    static LinkedList list;

    @BeforeClass
    public static void setUp() throws Exception {
        ClassLoader classLoader = LinkedList.class.getClassLoader();
        File file = new File(classLoader.getResource("forlist.txt").getFile());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        data = reader.readLine().split(" ");
        int[] arr = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();
        list = new LinkedList(arr);
    }


    @Test
    public void get() throws Exception {
        Assert.assertTrue(list.get(3) == 6);
    }

    @Test
    public void remove() throws Exception {
        list.remove(0);
        Assert.assertTrue(list.get(0) == 4);
    }

    @Test
    public void add() throws Exception {
        list.add(5);
        Assert.assertTrue(list.get(11) == 5);
    }

}