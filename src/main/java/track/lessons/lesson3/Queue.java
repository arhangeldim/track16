package track.lessons.lesson3;

public interface Queue {
    void enqueue(int value); // поместить элемент в очередь

    int dequeue(); // вытащить первый элемент из очереди
}
