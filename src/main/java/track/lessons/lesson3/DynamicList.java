package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    private int[] data = null;
    private int top = 0;

    static final int REALLOC_MEM_SIZE = 5;

    public DynamicList() {

    }

    public DynamicList(int defaultSize) {
        data = new int[defaultSize];
    }

    private void reallocateMemory() {
        if (data == null || data.length <= top) {
            int[] newData = new int[top + REALLOC_MEM_SIZE];
            if (newData == null) {
                throw new OutOfMemoryError("Memory can't be allocated");
            }
            if (data != null) {
                System.arraycopy(data, 0, newData, 0, top);
            }
            data = newData;
        }
    }

    @Override
    void add(int item) {
        if (data == null || top >= data.length) {
            reallocateMemory();
        }
        data[top++] = item;
    }

    @Override
    int remove(int idx) {
        if (idx < 0 || idx >= top) {
            throw new IndexOutOfBoundsException();
        }
        int value = data[idx];
        for (int i = idx + 1; i < top; i++) {
            data[i - 1] = data[i];
        }
        top--;
        return value;
    }

    @Override
    int get(int idx) {
        if (idx < 0 || idx >= top) {
            throw new IndexOutOfBoundsException();
        }

        return data[idx];
    }

    @Override
    int size() {
        return top;
    }
}
