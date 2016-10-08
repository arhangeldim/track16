package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    private int[] dynamicArray;
    private int size;
    private int buffer;

    @Override
    void add(int item) {
        if (buffer >= size + 1) {
            dynamicArray[size] = item;
            size++;
        } else {
            increaseBuffer();
            add(item);
        }
    }

    private void increaseBuffer() {
        if (buffer == 0) {
            buffer = 2;
            dynamicArray = new int[buffer];
        } else {
            buffer = buffer * 2;
            int[] newArray = new int[buffer];
            System.arraycopy(dynamicArray, 0, newArray, 0, size);
            dynamicArray = newArray;
        }
    }

    @Override
    int remove(int idx) {
        if (size < 1) {
            return -1;
        }
        int temp = dynamicArray[idx];
        System.arraycopy(dynamicArray, idx + 1, dynamicArray, idx, size - idx);
        size--;
        return temp;
    }

    @Override
    int get(int idx) {
        if (idx + 1 > size) {
            return -1;
        }
        return dynamicArray[idx];
    }

    @Override
    int size() {
        return size;
    }
}