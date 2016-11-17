package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    public abstract void add(int item); // - добавить элемент в конец списка

    public abstract int remove(int idx) throws IndexOutOfRangeException; // -  удалить элемент по индексу idx,
    // если idx некорректный напечатать ошибку,
    // если ок - вернуть удаленный элемент

    public abstract int get(int idx) throws IndexOutOfRangeException; // - получить элемент по индексу

    public abstract int size();

    public void addAll(int[] elements) {
        for (int element : elements) {
            add(element);
        }
    }
}
