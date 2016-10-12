package track.lessons.lesson3;

/**
 * Created by Андрей on 12.10.2016.
 */

// Очередь - структура данных, удовлетворяющая правилу First IN First OUT
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeue() throws Exception; // вытащить первый элемент из очереди
}