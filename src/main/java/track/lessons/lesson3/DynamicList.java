package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List implements Stack,Queue {

    private static final int DEFAULT_LENGTH = 20;

    private int[] elems;

    private int size;

    public DynamicList() {
        super();
        elems = new int[DEFAULT_LENGTH];
    }

    @Override
    public void add(int item) {
        ++size;
        if (size >= elems.length) {
            int[] newElems = new int[elems.length * 2];
            System.arraycopy(elems, 0, newElems, 0, elems.length);
            elems = newElems;
        }
        elems[size - 1] = item;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 | idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        int returnElem = elems[idx];
        System.arraycopy(elems, idx + 1, elems, idx, elems.length - idx - 1);
        --size;
        return returnElem;
    }

    @Override
    public int get(int idx) {
        if (idx < 0 | idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        return elems[idx];
    }


    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        int elem = -1;
        try {
            elem = get(0);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        remove(0);
        return elem;
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        int elem = get(size - 1);
        remove(size - 1);
        return elem;
    }

    @Override
    public int size() {
        return size;
    }
}
