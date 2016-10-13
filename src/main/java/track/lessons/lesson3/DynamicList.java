package track.lessons.lesson3;

/**
 *
 */

public class DynamicList {

    private int[] array = new int[1];
    private int size = 0;

    void add(int item) {
        if (array.length <= size + 1) {
            int[] buff = new int[array.length * 2];
            System.arraycopy(array, 0, buff, 0, size + 1);
            array = buff;
            array[size] = item;
        } else {
            array[size] = item;
        }
        size++;
    }

    int remove(int idx) {
        if (idx >= 0 && idx < size) {
            int deleted = array[idx];
            if (size * 2 < array.length) {
                int[] buff = new int[size + 10];
                System.arraycopy(array, 0, buff, 0, idx);
                System.arraycopy(array, idx + 1, buff, idx, size - idx - 1);
                array = buff;
            } else {
                for (int i = idx + 1; i < size; i++) {
                    array[i - 1] = array[i];
                }
            }
            size--;
            return deleted;
        } else {
            System.err.print("Bad index");
            return 0;
        }

    }

    int get(int idx) {
        return array[idx];
    }

    int size() {
        return size;
    }
}
