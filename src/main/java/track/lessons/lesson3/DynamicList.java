package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    private int[] array;
    private int capacity;

    public DynamicList() {
        capacity = 1;
        array = new int[capacity];
    }

    @Override
    public void add(int item) {
        if (size >= capacity) {
            capacity *= 2;
            int[] tmpArray = new int[capacity];
            System.arraycopy(array, 0, tmpArray, 0, size);
            array = tmpArray;
        }
        array[size] = item;
        size++;
        System.out.println(capacity);
    }

    @Override
    public int remove(int idx) {
        int elem = array[idx];
        System.arraycopy(array, idx + 1, array, idx, size - idx - 1);
        size--;
        return elem;
    }

    @Override
    public int get(int idx) {
        return array[idx];
    }
}
