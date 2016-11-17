package track.lessons.lesson3;

/**
 * Created by mannimarco on 11/10/2016.
 */
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeue(); // вытащить первый элемент из очереди
}
