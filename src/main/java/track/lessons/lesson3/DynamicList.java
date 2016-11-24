package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    public static final int DEFAULT_CAPACITY = 10;
    private int[] list;

    public DynamicList(int size) {
        int capacity = DEFAULT_CAPACITY;
        if (size > 0) {
            capacity = size;
        }
        list = new int[capacity];
    }

    public DynamicList() {
        list = new int[DEFAULT_CAPACITY];
    }

    @Override
    public void add(int item) {
        if (size == list.length) {
            int[] newList = new int[2 * size];
            System.arraycopy(list, 0, newList, 0, size);
            list = newList;
        }
        list[size++] = item;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0) {
            System.out.println("The index can't be less than 0");
            return -1;
        }
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        int item = list[idx];
        System.arraycopy(list, idx + 1, list, idx, --size - idx);
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx < 0) {
            System.out.println("The index can't be less than 0");
            return -1;
        }
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        return list[idx];
    }
}
