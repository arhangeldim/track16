package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    protected int size = 0;

    abstract void add(int item);

    abstract int remove(int idx);

    abstract int get(int idx);

    public int size() {
        return size;
    }
}
