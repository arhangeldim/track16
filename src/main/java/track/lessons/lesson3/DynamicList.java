package track.lessons.lesson3;

public class DynamicList extends List {

    private int[] array;
    private int size;

    DynamicList() {
        array = new int[0];
        size = 0;
    }

    @Override
    void add(int item) {
        if (size == 0) {
            array = new int[1];
            array[0] = item;
        } else {
            int[] newarray = new int[this.size + 1];
            System.arraycopy(array, 0, newarray, 0, size);
            newarray[size] = item;
            array = new int[size + 1];
            array = newarray;
        }
        size++;
    }

    @Override
    int remove(int idx) {
        if (idx < 0 || idx > size - 1) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            if (size == 0) {
                throw new IndexOutOfBoundsException("List is empty");
            } else {
                if (idx == 0) {
                    int[] first = new int[size - 1];
                    System.arraycopy(array, 1, first, 0, size - 1);
                    array = new int[size - 1];
                    array = first;
                } else {
                    if (idx == size - 1) {
                        int[] first = new int[size - 1];
                        System.arraycopy(array, 0, first, 0, size - 1);
                        array = new int[size - 1];
                        array = first;
                    } else {
                        int[] first = new int[idx];
                        int[] second = new int[size - 1 - idx];
                        int[] new_array = new int[size - 1];
                        System.arraycopy(array, 0, first, 0, idx);
                        System.arraycopy(array, idx + 1, second, 0, size - idx - 1);
                        System.arraycopy(first, 0, newarray, 0, idx);
                        System.arraycopy(second, 0, newarray, idx, size - idx - 1);
                        array = new int[size - 1];
                        array = newarray;
                    }
                }
                return 0;
            }
        }
    }

    @Override
    int get(int idx) {
        return array[idx];
    }

    @Override
    int size() {
        return size;
    }
}