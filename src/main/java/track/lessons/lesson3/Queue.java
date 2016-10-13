package track.lessons.lesson3;

/**
 * Created by no_love_no_problem on 13.10.16.
 */
public interface Queue {
    void enqueue(int value) throws Exception;
    int dequeue() throws Exception;
}
