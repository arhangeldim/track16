package track.lessons.lesson3;

/**
 * Created by les on 10/9/16.
 */

// Очередь - структура данных, удовлетворяющая правилу First IN First OUT
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди
}