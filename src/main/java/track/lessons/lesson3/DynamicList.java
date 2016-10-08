package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List implements Stack, Queue {
    private int[] list;
    private int maxSize;

    DynamicList(int size) {
        if (size > 0) {
            maxSize = size;
        } else {
            maxSize = 1;
        }
        list = new int[maxSize];
    }

    DynamicList() {
        maxSize = 1;
        list = new int[maxSize];
    }

    @Override
    public void add(int item) {
        if (maxSize == size) {
            maxSize *= 2;
            int[] newList = new int[maxSize];
            System.arraycopy(list, 0, newList, 0, size);
            list = newList;
        }
        list[size++] = item;
        //printList();
    }

    @Override
    public int remove(int idx) {
        if (!checkIdx(idx)) {
            System.out.print("Out of range\n");
            return 0;
        }

        int item = list[idx];
        System.arraycopy(list, idx + 1, list, idx, size-- - idx);
        return item;
    }

    @Override
    public int get(int idx) {
        if (!checkIdx(idx)) {
            System.out.print("Out of range\n");
            return 0;
        }
        return list[idx];
    }

    @Override
    public void printList() {
        System.out.print("List = { ");
        for (int item : list) {
            System.out.printf("%d ", item);
        }
        System.out.print("}\n");
    }

    public void push(int value) {
        add(value);
    }

    public int pop() {
        return remove(size - 1);
    }

    public void enqueue(int value) {
        if (maxSize == size) {
            maxSize *= 2;
            int[] newList = new int[maxSize];
            System.arraycopy(list, 0, newList, 1, size);
            list = newList;
        } else {
            System.arraycopy(list, 0, list, 1, size);
        }
        list[0] = value;
        size++;
    }

    public int dequeu() {
        return remove(0);
    }

}
