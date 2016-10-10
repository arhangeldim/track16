package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {

    private int[] dynamicArray = new int[0];
    private int size = 0;

    @Override
    public void add(int item) {
        if (dynamicArray.length >= size + 1) {
            dynamicArray[size] = item;
            size++;
        } else {
            increaseBuffer();
            add(item);
        }
    }

    private void increaseBuffer() {
        if (dynamicArray.length == 0) {
            dynamicArray = new int[2];
        } else {
            int[] newArray = new int[dynamicArray.length * 2];
            System.arraycopy(dynamicArray, 0, newArray, 0, size);
            dynamicArray = newArray;
        }
    }

    @Override
    public int remove(int idx) {
        if (size < 1 || idx < 0) {
            return -1;
        }
        int temp = dynamicArray[idx];
        System.arraycopy(dynamicArray, idx + 1, dynamicArray, idx, size - idx);
        size--;
        return temp;
    }

    @Override
    public int get(int idx) {
        if (idx + 1 > size || idx < 0) {
            return -1;
        }
        return dynamicArray[idx];
    }

    @Override
    public int size() {
        return size;
    }
}