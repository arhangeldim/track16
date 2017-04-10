package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
interface Queue<T> {
    void enqueue(T value);

    T dequeue();
}