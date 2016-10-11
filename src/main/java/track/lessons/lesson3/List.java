package track.lessons.lesson3;

/**
 *
 */
public abstract class List {

    public abstract void add(int item); //- добавить элемент в конец списка

    public abstract int remove(int idx); //- удалить элемент по индексу idx, если idx некорректный напечатать ошибку,
                                    // если ок - вернуть удаленный элемент

    public abstract int get(int idx); //- получить элемент по индексу

    public abstract int size();
}
