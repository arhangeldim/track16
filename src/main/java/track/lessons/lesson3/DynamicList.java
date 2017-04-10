package track.lessons.lesson3;

import java.util.*;

/**
 *
 */
public class DynamicList extends List {

    private int size = 10;
    private int countOfItems = 0;
    private int[] list  = new int[size];

    public void add(int item) {

        if (countOfItems + 1 == size) {
            size = ( 3 * size ) / 2 + 1;
            int[] transientList = new int[size];
            System.arraycopy(list,0,transientList,0,countOfItems);
            list = transientList;
        }
        list[countOfItems] = item;
        countOfItems++;
    }

    public int remove(int idx) {
        int result = list[idx];
        System.arraycopy(list,idx + 1, list,idx,countOfItems - idx);
        countOfItems--;
        return result;
    }

    public int get(int idx) {
        return list[idx];
    }

    public int size() {
        return countOfItems;
    }


}
