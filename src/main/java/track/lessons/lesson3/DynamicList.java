package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    DynamicList() {
        size = 0;
        buffer = new int[2];
    }

    @Override
    public void add(int item) throws Exception {
        if (size >= buffer.length) {
            int[] old = buffer;
            buffer = new int[2 * old.length];
            System.arraycopy(old, 0, buffer, 0, old.length);
        }
        buffer[size] = item;
        size += 1;
    }

    @Override
    public int remove(int idx) throws Exception {
        if (idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int res = buffer[idx];

        if (idx < size - 1) {
            System.arraycopy(buffer, idx, buffer, idx + 1, size - idx);
        }

        size--;
        return res;
    }

    @Override
    public int get(int idx) throws Exception {
        if (idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return buffer[idx];
    }

    private int[] buffer;
}
