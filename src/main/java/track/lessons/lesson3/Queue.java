package track.lessons.lesson3;

/**
 * Created by user on 08.10.16.
 */
public interface Queue {
    public void enqueue(int value); // поместить элемент в очередь

    public int dequeu(); // вытащить первый элемент из очереди
}