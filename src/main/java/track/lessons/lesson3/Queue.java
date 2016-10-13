package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */

// Очередь - структура данных, удовлетворяющая правилу First IN First OUT
interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди
}
