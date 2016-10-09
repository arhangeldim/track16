package track.lessons.lesson3;

/**
 * Created by oleg on 08.10.16.
 */
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь
    int dequeu(); // вытащить первый элемент из очереди
}
