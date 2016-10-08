package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private int[] list;
    //private int end; //It is the last free item. If the list is crowded, it is the list size.

    public DynamicList(int size) {
        int maxSize = 1;
        if (size > 0) {
            maxSize = size;
        }
        this.list = new int[maxSize];
    }

    public DynamicList() {
        this.list = new int[1];
    }



    public int[] getList() {
        return list;
    }




    @Override
    public void add(int item) {
        if (size == list.length) {
            int[] newList = new int[2 * size];
            System.arraycopy(list, 0, newList, 0, size);
            list = newList;
        }
        list[size++] = item;
    }

    @Override
    public int remove(int idx) {
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        int item = list[idx];
        System.arraycopy(list, idx + 1, list, idx, size-- - idx);
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        return list[idx];
    }
}
