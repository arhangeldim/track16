package track.lessons.lesson3;

import java.text.MessageFormat;

/**
 *
 */
public class DynamicList extends List {
    private int[] elements = new int[0];
    private int size = 0;

    private void checkIndex(int idx, String action) throws IndexOutOfRangeException {
        if (0 > idx || idx >= size) {
            throw new IndexOutOfRangeException(MessageFormat.format("You want to {0} {1} element from" +
                    " dynamic list with {2} elements.", action, Integer.toString(idx),
                    Integer.toString(size)));
        }
    }

    public void add(int item) {
        if (size >= elements.length) {
            int elementsSize = elements.length == 0 ? 1 : elements.length * 2;
            int[] newArray = new int[elementsSize];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
        elements[size] = item;
        size++;
    }

    public int remove(int idx)  throws IndexOutOfRangeException {
        checkIndex(idx, "remove");
        int item = elements[idx];
        System.arraycopy(elements, idx + 1, elements, idx, size - idx - 1);
        size--;
        return item;
    }

    public int get(int idx) throws IndexOutOfRangeException {
        checkIndex(idx, "remove");
        return elements[idx];
    }

    public int size() {
        return size;
    }
}
