package track.lessons.lesson3;

import track.lessons.lesson3.List;

/**
 *
 */
public class DynamicList extends List {
    final int baseSize = 5;

    int[] data;

    int allocSize;

    int size;

    public DynamicList() {
        data = new int[baseSize];
        size = 0;
        allocSize = baseSize;
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
        size += 1;
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
        size += 1;
        if (conditionForIncrease()) {
            allocateMore();
        }
    }

    public int remove(int idx) throws Exception {
        if (! (0 <= idx && idx < size)) {
            throw new Exception("No element of such index:" + idx);
        }
        int ret = data[idx];
        System.arraycopy(data, idx + 1, data, idx, size - (idx + 1));
        size -= 1;
        return ret;
    }

    public int get(int idx) throws Exception {
        if (! (0 <= idx && idx < size)) {
            throw new Exception("No element of such index:" + idx);
        }
        return data[idx];
    }

    public int size() {
        return size;
    }
}
