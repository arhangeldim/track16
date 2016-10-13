package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    int size;
    // добавить элемент в конец списка
    abstract void add(int item);
    // удалить элемент по индексу idx, если idx некорректный напечатать ошибку, если ок - вернуть удаленный элемент
    abstract int remove(int idx);
    // получить элемент по индексу
    abstract int get(int idx);
    //распечать список
    abstract void print();
    // сколько элементов в данный момент в списке
    int size()
    {
        return size;
    }
    boolean isValid(int idx)
    {
        if(idx < 0 || idx >= size)
        {
            throw new IndexOutOfBoundsException("Error: Index is unavailable");
        }
        return true;
    }
}
