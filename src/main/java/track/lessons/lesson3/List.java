package track.lessons.lesson3;

/**
 *
 */
public abstract class List {

    protected int size = 0;

    protected void validateIndex(int idx) {
        if (!isValidIndex(idx)) {
            throw new IllegalArgumentException("Index must be in [0, size]");
        }
    }

    protected boolean isValidIndex(int idx) {
        return 0 <= idx && idx < size;
    }

    //добавить элемент в конец списка
    abstract void add(int item);

    //удалить элемент по индексу idx,
    // если idx некорректный напечатать ошибку,
    // если ок - вернуть удаленный элемент
    abstract int remove(int idx);

    //получить элемент по индексу
    abstract int get(int idx);

    //сколько элементов в данный момент в списке
    abstract int size();
}
