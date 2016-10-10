package track.lessons.lesson3;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by geoolekom on 09.10.16.
 */
public class ListTest {

    @Test
    public void dynamicTest() throws Exception {
        DynamicList<Integer> dynList = new DynamicList<Integer>();
        for(int i = 0; i < 500; i++) {
            dynList.add(i);
            //Проверяем, не падает ли при переполнении и правильно ли записыввает
            Assert.assertEquals(i, (int) dynList.get(i));
        }

        Assert.assertEquals(499, (int) dynList.get(499));
        Assert.assertEquals(500, dynList.size());   //Проверяем, правильно ли возвращается размер

        dynList.remove(250);
        Assert.assertEquals(251, (int) dynList.get(250)); //Проверяем, нормально ли удаляет из середины

        dynList.remove(0);
        Assert.assertEquals(1, (int) dynList.get(0));     //Проверяем, нормально ли удаляет начало

        dynList.remove(497);
        Assert.assertEquals(498, (int) dynList.get(496));     //Проверяем, нормально ли удаляет конец

        Assert.assertEquals(null, dynList.remove(500));
        Assert.assertEquals(null, dynList.remove(-10));
        Assert.assertEquals(null, dynList.get(500));
        Assert.assertEquals(null, dynList.get(-10));         //Проверяем, ругнется ли при несанкционируемом доступе
    }
    
    @Test
    public void linkedTest() {
        LinkedList<Integer> linList = new LinkedList<Integer>();
        for(int i = 0; i < 500; i++) {
            linList.add(i);
            //Проверяем, не падает ли при переполнении и правильно ли записыввает
            Assert.assertEquals(i, (int) linList.get(i));
        }

        Assert.assertEquals(500, linList.size());   //Проверяем, правильно ли возвращается размер

        linList.remove(250);
        Assert.assertEquals(251, (int) linList.get(250)); //Проверяем, нормально ли удаляет из середины

        linList.remove(0);
        Assert.assertEquals(1, (int) linList.get(0));     //Проверяем, нормально ли удаляет начало

        linList.remove(497);
        Assert.assertEquals(498, (int) linList.get(496));     //Проверяем, нормально ли удаляет конец

        Assert.assertEquals(null, linList.remove(500));
        Assert.assertEquals(null, linList.remove(-10));
        Assert.assertEquals(null, linList.get(500));
        Assert.assertEquals(null, linList.get(-10));     //Проверяем, ругнется ли при несанкционируемом доступе

    }

    @Test
    public void interfacesTest() {
        Stack<Integer> stackList = new LinkedList<Integer>();
        Queue<Integer> queueList = new LinkedList<Integer>();

        stackList.push(1);
        stackList.push(2);

        queueList.enqueue(1);
        queueList.enqueue(2);

        Assert.assertEquals(2, (int) stackList.pop());
        Assert.assertEquals(1, (int) queueList.dequeue());
    }
}
