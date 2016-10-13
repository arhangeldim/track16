package track.lessons.lesson3;

import track.lessons.lesson3.Queue;
import track.lessons.lesson3.Stack;

/**
 *
 */
public abstract class List implements Stack, Queue {
    public abstract void add(int item);
    public abstract int remove(int idx) throws Exception;
    public abstract int get(int idx) throws Exception;
    public abstract int size();
}
