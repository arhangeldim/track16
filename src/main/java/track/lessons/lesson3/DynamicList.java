package track.lessons.lesson3;


/**
 *
 */
public class DynamicList extends List {
    private int[] items;
    private int size;

    DynamicList() {
        items = new int[1];
        size = 0;
    }

    /**
     * добавить элемент в конец списка
     */
    @Override
    void add(int item) {
        if (size >= items.length) {
            int[] newItems = new int[items.length * 2];
            System.arraycopy(items, 0, newItems, 0, size);
            newItems[size] = item;
            items = newItems;
        } else {
            items[size] = item;
        }
        size++;
    }

    /**
     * удалить элемент по индексу idx, если idx некорректный напечатать ошибку, если ок - вернуть удаленный элемент
     */
    @Override
    int remove(int idx) {
        if (idx >= 0 && idx < size) {
            int[] newItems = new int[items.length - 1];
            System.arraycopy(items, 0, newItems, 0, idx);
            System.arraycopy(items, idx + 1, newItems, 0, items.length - 1 - idx);
            int removedElem = items[idx];
            items = newItems;
            size--;
            return removedElem;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * получить элемент по индексу
     */
    @Override
    int get(int idx) {
        if (idx >= 0 && idx < size) {
            return items[idx];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * сколько элементов в данный момент в списке
     */
    @Override
    int size() {
        return size;
    }
}
