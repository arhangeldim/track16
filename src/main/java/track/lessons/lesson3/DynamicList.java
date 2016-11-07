package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */
public class DynamicList extends List {

    private int[] array;

    DynamicList(int size) {
        array = new int[size];
    }

    DynamicList() {
        this(DEFAULT_SIZE);
    }

    @Override
    public void add(int item) {
        if (size >= array.length) {
            int[] newArray = new int[2 * array.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size] = item;
        size++;

    }

    @Override
    public int remove(int idx) throws WrongIndexException {
        checkIndex(idx);
        int removeElement = array[idx];
        System.arraycopy(array, idx + 1, array, idx, array.length - idx - 1);
        size--;
        return removeElement;
    }

    @Override
    public int get(int idx) throws WrongIndexException {
        checkIndex(idx);
        return array[idx];
    }


}
