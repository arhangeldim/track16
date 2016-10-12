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
        if (data.length <= top) {
            int[] newData = new int[top + REALLOC_MEM_SIZE];
            if (newData == null) {
                throw new OutOfMemoryError("Memory can't be allocated");
            }
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    @Override
    void add(int item) {
        if (top >= data.length) {
            reallocateMemory();
        }
        data[top++] = item;
    }

    @Override
    int remove(int idx) {
        if (idx < 0 || idx >= top) {
            return -1;
        }
        for (int i = idx + 1; i < top; i++) {
            data[i - 1] = data[i];
        }
        top--;
        return 0;
    }

    @Override
    int get(int idx) {
        /*
        Тут надо обработать ошибку
        if(idx < 0 || idx >= top)
         */
        return data[idx];
    }

    @Override
    int size() {
        return top;
    }
}
