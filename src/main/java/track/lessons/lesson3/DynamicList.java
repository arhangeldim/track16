package track.lessons.lesson3;

/**
 *
 */

public class DynamicList extends List {

    public static final int DEFAULT_CAPACITY = 2;

    private int[] array = new int[DEFAULT_CAPACITY];

    public int size() {
        return array.length;
    }

    public void add(int item) {

        int[] newArray = new int[array.length + 1]; // size new int[]{1,2,3,5,5};
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = item;
        array = newArray;
    }

    int get(int idx) {
        return array[idx];
    }

    int remove(int idx) {

        if (idx < 0 || idx >= array.length) {
            System.out.println("ERROR");
            return -1;
        }
        if (idx != 0) {
            int[] newArray = new int[array.length - 1];
            System.arraycopy(array, 0, newArray, 0, idx);
            System.arraycopy(array, idx + 1, newArray, idx, array.length - idx);
            array = newArray;
            return 0;
        } else {

            int[] newArray = new int[array.length - 1];
            System.arraycopy(array, idx + 1, newArray, 0, array.length - 1);
            return 0;
        }
    }

}

