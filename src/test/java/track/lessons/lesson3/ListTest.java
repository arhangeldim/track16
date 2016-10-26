package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */

import org.junit.*;
import org.junit.runner.notification.RunListener;

public class ListTest {


    @Test
    public void dynamicListTest() {

        //проверка конструкторов
        DynamicList list = new DynamicList(15);
        assert (list.size() == 0);
       // assert (list.getCapacity() == 15);

        list = new DynamicList();
        assert (list.size() == 0);
        //assert (list.getCapacity() == 10);

        //проверка методов get и add
        for (int i = 0; i < 21; i++) {
            list.add(i);
            assert (list.get(i) == i);
//            if (i == 9) {
//              //  assert (list.getCapacity() == 10);
//            }
//            if (i == 10) {
//               // assert (list.getCapacity() == 20);
//            }
//            if (i == 20) {
//               // assert (list.getCapacity() == 40);
//            }
        }


        //проверка метода remove
        int delete = list.remove(19);
        assert (list.size() == 20);
        assert (list.get(19) == 20);
        assert (delete == 19);

        delete = list.remove(0);
        assert (list.size() == 19);
        assert (list.get(0) == 1);
        assert (delete == 0);

        //проверка exceptions
        boolean excep = false;
        try {
            list.get(-1);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

        excep = false;
        try {
            list.get(100);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

        excep = false;
        try {
            list.remove(100);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

    }

    @Test
    public void linkedListTest() {
        //проверка конструкторов
        List list = new LinkedList();
        assert (list.size() == 0);


        //проверка методов add
        for (int i = 0; i < 21; i++) {
            list.add(i);
        }


        //проверка метода remove и get
        int delete = list.remove(19);
        assert (list.size() == 20);
        assert (list.get(19) == 20);
        assert (delete == 19);

        delete = list.remove(0);
        assert (list.size() == 19);
        assert (list.get(0) == 1);
        assert (delete == 0);

        delete = list.remove(18);
        assert (list.size() == 18);
        assert (list.get(17) == 18);
        assert (delete == 20);

        //проверка exceptions
        boolean excep = false;
        try {
            list.get(-1);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

        excep = false;
        try {
            list.get(18);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

        excep = false;
        try {
            list.remove(100);
        } catch (IllegalArgumentException ex) {
            excep = true;
        }
        Assert.assertTrue(excep);

    }

    @Test
    public void queueTest() {

        Queue queue = new LinkedList();

        for (int i = 0; i < 21; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 21; i++) {
            Assert.assertEquals(queue.dequeu(), i);
        }

        boolean excep = false;
        try {
            queue.dequeu();
        } catch (IllegalArgumentException ex) {
            excep = true;
        }

        Assert.assertTrue(excep);

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

        boolean excep = false;
        try {
            stack.pop();
        } catch (IllegalArgumentException ex) {
            excep = true;
        }

        Assert.assertTrue(excep);

    }

}
