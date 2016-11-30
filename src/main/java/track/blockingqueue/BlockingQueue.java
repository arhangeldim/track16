package track.blockingqueue;

/**
 * Created by geoolekom on 18.11.16.
 */
public interface BlockingQueue<T> {

    int SIZE = 10;

    void put(T item);

    boolean offer(T item);

    T take();

    T poll(int timeout);

}
