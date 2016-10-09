package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
abstract class List<T> {

    protected int size;

    abstract void add(T item);

    abstract T remove(int index);

    abstract T get(int index);

    int size() {
        return size;
    }
}
