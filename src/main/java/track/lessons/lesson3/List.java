package track.lessons.lesson3;

/**
 *
 */
public abstract class List {

    protected int size = 0;

    public abstract void add(int item);

    public abstract int remove(int idx);

    public abstract int get(int idx);

    public int size() {
        return size;
    }
}
