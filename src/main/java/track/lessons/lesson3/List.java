package track.lessons.lesson3;

/**
 *
 */
abstract class List {
    public static final int INVALID_VALUE = -1;

    abstract void add(int item);

    abstract int remove(int idx);

    abstract int get(int idx);

    abstract int size();
}