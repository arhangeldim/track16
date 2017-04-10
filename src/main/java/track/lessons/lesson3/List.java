package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
abstract class List<T> {

    protected int size;

    public abstract void add(T item);

    public abstract T remove(int index);

    public abstract T get(int index);

    public int size() {
        return size;
    }
}
