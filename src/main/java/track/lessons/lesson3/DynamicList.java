package track.lessons.lesson3;

public class DynamicList extends List {

    private static int defaultsize = 10;
    private int[] array;
    private int size;

    public DynamicList(int size) {
        if (size > 0) {
            array = new int[size];
        } else {
            array = new int[defaultsize];
        }
        this.size = 0;
    }

    public DynamicList() {
        array = new int[defaultsize];
        size = 0;
    }


    @Override
    void add(int item) {
        if (size == array.length) {
            int[] newArray = new int[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        array[size++] = item;
    }

    @Override
    int remove(int idx) {
        int value;
        if (!isRange(idx)) {
            value = array[idx];
            System.arraycopy(array, idx + 1, array, idx, size - idx - 1);
            size--;
        } else {
            value = -1;
        }
        return value;
    }

    private boolean isRange(int idx) {
        return idx < 0 || idx > size - 1;
    }

    @Override
    int get(int idx) {
        if (!isRange(idx)) {
            return array[idx];
        } else {
            throw new IndexOutOfBoundsException("Index is incorrect");
        }
    }

    @Override
    int size() {
        return size;
    }
}