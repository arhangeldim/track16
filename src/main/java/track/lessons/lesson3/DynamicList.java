package track.lessons.lesson3;

import track.lessons.lesson3.List;

/**
 *
 */
public class DynamicList extends List {
    public static final int baseSize = 5;

    private int[] data;

    int allocSize;

    int size;

    public DynamicList() {
        data = new int[baseSize];
        size = 0;
        allocSize = baseSize;
    }

    public DynamicList(int listsize) {
        if (listsize <= 0) {
            data = new int[baseSize];
            size = 0;
            allocSize = baseSize;
        }
        allocSize = listsize * 2;
        data = new int[allocSize];
        size = listsize;
        for (int i = 0; i < size; i++){
            data[i] = 0;
        }
    }

    @Override
    public int pop() throws Exception {
        return remove(size - 1);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public void enqueue(int value) {
        insert(0, value);
    }

    @Override
    public int dequeu() throws Exception {
        return pop();
    }

    private void insert(int idx, int val) {
        System.arraycopy(data, idx, data, idx + 1, size - idx);
        size++;
        data[idx] = val;
        if (conditionForIncrease()) {
            allocateMore();
        }
    }

    private boolean conditionForIncrease() {
        return allocSize - size <= 1;
    }

    private void allocateMore() {
        int newAllocSize = allocSize * 2;
        int[] newData = new int[newAllocSize];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
        allocSize = newAllocSize;
    }

    public void add(int item) {
        data[size] = item;
        size++;
        if (conditionForIncrease()) {
            allocateMore();
        }
    }

    public int remove(int idx) throws Exception {
        if (! (0 <= idx && idx < size)) {
            throw new IllegalArgumentException("No element of such index:" + idx);
        }
        int ret = data[idx];
        System.arraycopy(data, idx + 1, data, idx, size - (idx + 1));
        size--;
        return ret;
    }

    public int get(int idx) throws Exception {
        if (! (0 <= idx && idx < size)) {
            throw new IllegalArgumentException("No element of such index:" + idx);
        }
        return data[idx];
    }

    public int size() {
        return size;
    }
}
