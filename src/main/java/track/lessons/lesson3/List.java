package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    abstract void add ( int item) throws Exception;

    abstract int remove(int idx)  throws Exception;

    abstract int get(int idx)  throws Exception;

    int size() {
        return size;
    }

    protected int size;
}
