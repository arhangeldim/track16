package track.lessons.lesson3;

/**
 *
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
