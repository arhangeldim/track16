package track.lessons.lesson3;

/**
 *
 */
public abstract class List {

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
