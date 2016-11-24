package track.lessons.lesson3;

/**
 *
 */
abstract class List {
    protected int size;

    abstract void add(int value);

    abstract int remove(int index);

    abstract int get(int index);

    public int getSize() {
        return size;
    }
}
