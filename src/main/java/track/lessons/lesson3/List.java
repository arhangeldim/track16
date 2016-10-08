
package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    abstract public void add(int item); //- добавить элемент в конец списка

    abstract public int remove(int idx); //- удалить элемент по индексу idx, если idx некорректный напечатать ошибку,

    abstract public int get(int idx); //- получить элемент по индексу

    abstract public int size(); //- сколько элементов в данный момент в списке
}