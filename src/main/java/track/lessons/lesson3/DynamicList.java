package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    private int[] data = null;

    private static final int DEFAULT_SIZE = 2;

    public DynamicList() {
        this(DEFAULT_SIZE);
    }

    public DynamicList(int defaultSize) {
        data = new int[defaultSize];
    }

    private boolean isValid() {
        return data != null && data.length > size;
    }

    private void reallocateMemory() {
        if (!isValid()) {
            int[] newData = new int[data.length * 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    @Override
    void add(int item) {
        if (!isValid()) {
            reallocateMemory();
        }
        data[size++] = item;
    }

    @Override
    int remove(int idx) {
        validateIndex(idx);
        int value = data[idx];
        System.arraycopy(data, idx + 1, data, idx + 1 - 1, size - (idx + 1));
        size--;
        return value;
    }

    @Override
    int get(int idx) {
        validateIndex(idx);
        return data[idx];
    }

    @Override
    int size() {
        return size;
    }
}
