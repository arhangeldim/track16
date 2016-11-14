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
    public int remove(int index) {
        if (!checkIndex(index)) {
            System.out.print("Out of range\n");
            return 0;
        }

        int item = list[index];
        System.arraycopy(list, index + 1, list, index, --size - index);
        return item;
    }

    @Override
    public int get(int index) {
        if (!checkIndex(index)) {
            System.out.print("Out of range\n");
            return 0;
        }
        return list[index];
    }

    @Override
    public void printList() {
        System.out.print("{ ");
        for (int index = 0; index < size; index++) {
            System.out.printf("%d ", list[index]);
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
}
