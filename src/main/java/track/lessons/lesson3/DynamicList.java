package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private int[] array = new int[1];
    private int elementsNumber = 0;
    private int arraySize = 1;

    public void add(int item) {
        if (elementsNumber >= arraySize) {
            arraySize *= 2;
            int[] newArray = new int[arraySize];
            System.arraycopy(array, 0, newArray, 0, elementsNumber);
            array = newArray;
        }
        array[elementsNumber] = item;
        elementsNumber++;
    }

    public int remove(int idx)  throws Exception {
        if (0 <= idx && idx < elementsNumber) {
            int item = array[idx];
            System.arraycopy(array, idx + 1, array, idx, elementsNumber - idx - 1);
            elementsNumber--;
            return item;
        } else {
            throw new Exception();
        }
    }

    public int get(int idx) throws Exception {
        if (0 <= idx && idx < elementsNumber) {
            return array[idx];
        } else {
            throw new Exception();
        }
    }

    public int size() {
        return elementsNumber;
    }
}
