package track.lessons.lesson3;

/**
 *
 */
abstract public class List {
    abstract void add(int item); // - добавить элемент в конец списка
    abstract int remove(int idx) throws Exception; // -  удалить элемент по индексу idx,
    // если idx некорректный напечатать ошибку,
    // если ок - вернуть удаленный элемент
    abstract int get(int idx) throws Exception; // - получить элемент по индексу
    abstract int size();
}
