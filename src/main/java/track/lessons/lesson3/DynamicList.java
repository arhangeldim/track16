package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    DynamicList(int size) {
        if (size > 0) {
            array = new int[size];
            this.size = size;
            this.header = 0;
        } else {
            throw new Error("Size is negative!");
        }
    }

    DynamicList() {
        array = new int[10];
        this.size = 10;
        this.header = 0;
    }

    @Override
    void add(int item) {
        if (header >=  size) {
            int[] array1 = new int[size * 2];
            System.arraycopy(array, 0, array1, 0, size);
            array = array1;
            size = size * 2;

        }
        array[header] = item;
        header++;
    }

    @Override
    int remove(int idx) {
        if (idx < header) {
            int ans = array[idx];
            System.arraycopy(array, idx + 1, array, idx, header - idx);
            header--;
            return  ans;
        } else {
            throw new Error("Index is bigger than size!");
        }
    }

    @Override
    int get(int idx) {
        if (idx < header) {
            return array[idx];
        } else {
            throw new Error("Index is bigger than size!");
        }
    }

    @Override
    int size() {
        return header;
    }

    private int[] array;
    private int size;
    private int header;
}
