package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int INVALID_VALUE = -1;
    private int capacity;
    private int size;
    private int[] array;


    /**
     * @param initialCapacity Initial dynamic array capacity
     */
    public DynamicList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.capacity = initialCapacity;
        } else {
            this.capacity = DEFAULT_CAPACITY;
        }
        this.array = new int[capacity];
    }

    public DynamicList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void add(int item) {
        if (size == capacity) {
            capacity *= 2;
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size] = item;
        size++;
    }

    @Override
    public int remove(int idx) {
        int value;
        if (isWithinRange(idx)) {
            value = array[idx];
            System.arraycopy(array, idx + 1, array, idx, size - idx - 1);
            size--;
        } else {
            value = INVALID_VALUE;
        }
        return value;
    }

    @Override
    public int get(int idx) {
        return isWithinRange(idx) ? array[idx] : INVALID_VALUE;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @param idx The index of the item to check if it is within boundaries of the list
     * @return true if the index idx is within boundaries of the list, false otherwise
     */
    private boolean isWithinRange(int idx) {
        return (idx >= 0) && (idx < size);
    }
}
