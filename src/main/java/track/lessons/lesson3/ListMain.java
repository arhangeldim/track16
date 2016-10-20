package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {

        //Создаем листы 2-х типов
        List linkedList = new LinkedList();
        List dynamicLict = new DynamicList();
        //Пример добаления
        for (int i = 0;i < 10;i++) {
            linkedList.add(i);
            dynamicLict.add(i);
        }
        //Пример получения
        linkedList.get(5);
        dynamicLict.get(7);
        //Пример удаления
        linkedList.remove(0);
        dynamicLict.remove(6);
        //Cтек
        Stack stack = (Stack) linkedList;
        stack.push(8);
        stack.pop();
        //Очередь
        Queue queue = (Queue) linkedList;
        queue.enqueue(9);
        queue.dequeu();

    }
}
