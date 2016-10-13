package track.lessons.lesson3;

/**
 *
 */
abstract class List {
    public static final int INVALID_VALUE = -1;

    abstract public void add(int item);

    abstract public int remove(int idx);

    abstract public int get(int idx);

    abstract public int size();
}