package track.lessons.lesson3;

public class DynamicList extends List {

    private int[] data;
    private int capacity;

    private static final int capacityDefault = 16;

    public DynamicList() {
        size = 0;
        capacity = capacityDefault;
        data = new int[capacity];
    }

    @Override
    public void add(int elem) {
        if (size >= capacity) {
            capacity <<= 1;
            int[] tempData = new int[capacity];
            System.arraycopy(data, 0, tempData, 0, size);
            data = tempData;
        }
        data[size++] = elem;
    }

    @Override
    public int remove(int index) {
        if (!checkIndex(index)) {
            return 0;
        } else {
            int returnedValue = get(index);

            if (size < (capacity >> 1)) {
                capacity >>= 1;
                int[] tempData = new int[capacity];
                System.arraycopy(data, 0, tempData, 0, size);
                data = tempData;
            }

            System.arraycopy(data, index + 1, data, index, size-- - index - 1);
            return returnedValue;
        }
    }

    @Override
    public int get(int index) {
        if (!checkIndex(index)) {
            return 0;
        }

        return data[index];
    }
}
