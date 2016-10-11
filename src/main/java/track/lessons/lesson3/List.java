package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    abstract void add(int item);

    abstract int remove(int idx);

    abstract int get(int idx);

    abstract int size();

    public abstract void dump();
}
