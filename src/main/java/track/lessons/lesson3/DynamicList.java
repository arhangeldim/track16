package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List implements Stack, Queue {
    private int[] list;
    private int maxSize;

    public DynamicList(int size) {
        if (size > 0) {
            maxSize = size;
        } else {
            maxSize = 1;
        }
        list = new int[maxSize];
    }

    public DynamicList() {
        maxSize = 1;
        list = new int[maxSize];
    }

    @Override
    public void add(int item) {
        if (maxSize == size) {
            resize();
        }
        list[size++] = item;
    }

    @Override
    public int remove(int idx) {
        if (!checkIdx(idx)) {
            System.out.print("Out of range\n");
            return 0;
        }

        int item = list[idx];
        System.arraycopy(list, idx + 1, list, idx, --size - idx);
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

    private int firstIdx = 0;

    public void enqueue(int value) {
        add(value);
    }

    public int dequeu() {
        return remove(0);
    }

    private void resize() {
        maxSize *= 2;
        int[] newList = new int[maxSize];
        System.arraycopy(list, 0, newList, 0, size);
        list = newList;
    }

    private void shift(int firstIdx) {
        System.arraycopy(list, firstIdx, list, 0, size - firstIdx);
        firstIdx = 0;
    }

}
