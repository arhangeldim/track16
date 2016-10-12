package track.lessons.lesson3;

/**
 * Created by altair on 12.10.16.
 */
// Очередь - структура данных, удовлетворяющая правилу First IN First OUT
interface Queue {

    // поместить элемент в очередь
    void enqueue(int value);

    // вытащить первый элемент из очереди
    int dequeu();
}