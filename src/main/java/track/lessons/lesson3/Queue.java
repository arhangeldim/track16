package track.lessons.lesson3;

/**
 * Created by mif-a on 08.10.2016.
 */
public interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди
}
