package track.lessons.lesson3;

import java.util.Arrays;

/**
 *
 */
public class DynamicList extends List {

    private static final int DEFAULT_CAPACITY = 10;
    private int[] elements;
    private int size;

    public DynamicList() {
        elements = new int[DEFAULT_CAPACITY];
    }

    public DynamicList(int capacity) {
        if (capacity >= 0) {
            elements = new int[capacity];
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void add(int item) {
        if (size == elements.length) {
            resize(2 * elements.length);
        }
        elements[size++] = item;
    }

    private void resize(int capacity) {
        int[] copy = new int[capacity];
        System.arraycopy(elements, 0, copy, 0, size);
        elements = copy;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        int item = elements[idx];
        System.arraycopy(elements, idx + 1, elements, idx, size-- - idx);
        if (size > 0 && size == elements.length / 4) {
            resize(elements.length / 2);
        }
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return elements[idx];
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

    @Override
    public String toString() {
        int[] elems = new int[size];
        System.arraycopy(elements, 0, elems, 0, size);
        return "DynamicList{" +
                "elements=" + Arrays.toString(elems) +
                ", size=" + size +
                ", capacity=" + elements.length +
                '}';
    }
}
