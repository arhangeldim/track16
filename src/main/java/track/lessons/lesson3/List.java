package track.lessons.lesson3;

/**
 *Created by Konstantin on 13.10.2016.
 */

public abstract class List {

    protected static final int DEFAULT_SIZE = 10;
    protected int size;

    public abstract void add(int item);

    public abstract int remove(int idx);

    public abstract int get(int idx);

    public int size() {
        return size;
    }
}
