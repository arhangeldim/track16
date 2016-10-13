package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */
public class DynamicList extends List {

    int[] array;
    int capacity;

    DynamicList(int size) {
        array = new int[size];
        capacity = size;
    }

    DynamicList() {
        this(DEFAULT_SIZE);
    }

    @Override
    public void add(int item) {

        if (size >= capacity) {
            capacity = 2 * capacity;
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size] = item;
        size++;

    }

    @Override
    public int remove(int idx) {
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException("No such index in array");
        }

        int removeElement = array[idx];
        System.arraycopy(array, idx + 1, array, idx, array.length - idx - 1);
        size--;
        return removeElement;
    }

    @Override
    public int get(int idx) {
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException("No such index in array");
        }

        return array[idx];
    }
}
