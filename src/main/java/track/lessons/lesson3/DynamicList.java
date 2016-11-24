package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private static final int INITIAL_SIZE = 10;
    int[] data;
    int size; //размер массива
    int last; //индекс последнего элемента

    public DynamicList() {
        data = new int[INITIAL_SIZE];
        last = -1;
        size = INITIAL_SIZE;
    }

    public DynamicList(int[] data) {
        this.data = data.clone();
        size = data.length;
        last = data.length - 1;
    }

    private void expandArray() {
        int[] newData = new int[size * 2];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
        size *= 2;
    }

    @Override
    public void add(int item) {
        last++;
        if (last == size) {
            expandArray();
        }
        data[last] = item;
    }

    public void dump() {
        for (int i = 0; i <= last; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    @Override
    public int remove(int idx) throws IndexOutOfBoundsException {
        if (!isIndexCorrect(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }
        int element;
        element = data[idx];
        data[idx] = 0;
        for (int i = idx; i < last; i++) {
            data[i] = data[i + 1];
        }
        last--;
        return element;
    }

    private boolean isIndexCorrect(int idx) {
        return idx >= 0 && idx <= last;
    }

    @Override
    public int get(int idx) {
        if (!isIndexCorrect(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }
        return data[idx];
    }

    @Override
    public int size() {
        return last + 1;
    }

}
