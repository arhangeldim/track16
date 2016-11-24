package track.lessons.lesson3;

/**
 * Created by frystile on 13.10.16.
 */
public abstract class List {
    abstract void add(int item); //- добавить элемент в конец списка

    abstract int remove(int idx); //- удалить элемент по индексу idx

    abstract int get(int idx); //- получить элемент по индексу

    abstract int size();  // - сколько элементов в данный момент в списк
}
