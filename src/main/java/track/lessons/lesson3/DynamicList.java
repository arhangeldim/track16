package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    public static final int DEFAULT_SIZE = 10;
    private int[] array;
    private int capacity;
    private int size;

    public DynamicList(int cap) {
        if (cap > 0) {
            array = new int[cap];
            capacity = cap;
        } else {
            throw new IllegalArgumentException("Capacity cannot be negative!");
        }
    }

    public DynamicList() {
        array = new int[DEFAULT_SIZE];
        capacity = DEFAULT_SIZE;
    }

    @Override
    void add(int item) {
        if (size >=  capacity) {
            int[] array1 = new int[capacity * 2];
            System.arraycopy(array, 0, array1, 0, capacity);
            array = array1;
            capacity = capacity * 2;

        }
        array[size] = item;
        size++;
    }

    @Override
    int remove(int idx) {
        if (idx < size) {
            int ans = array[idx];
            System.arraycopy(array, idx + 1, array, idx, size - idx);
            size--;
            return  ans;
        } else {

            throw new IllegalArgumentException("Index cannot be bigger than size!");
        }
    }

    @Override
    int get(int idx) {
        if (idx < size) {
            return array[idx];
        } else {

            throw new IllegalArgumentException("Index cannot be bigger than size!");
        }
    }

    @Override
    int size() {
        return size;
    }
}
