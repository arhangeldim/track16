package track.lessons.lesson3;

/**
 * Created by pavel on 10.10.16.
 */

interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди
}