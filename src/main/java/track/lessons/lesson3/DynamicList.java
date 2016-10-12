package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private int length = 0;
    private int bufferSize = 1;
    private int[] buffer = new int[bufferSize];

    private void reallocate(int newSize) {
        int[] newBuffer = new int[newSize];
        System.arraycopy(buffer, 0, newBuffer, 0, length);
        buffer = newBuffer;
        bufferSize = newSize;
    }

    @Override
    public void add(int item) {
        if (length == bufferSize) {
            reallocate(bufferSize * 2);
        }

        buffer[length++] = item;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx >= length) {
            printLengthError();
            return 0;
        }

        if (length * 4 <= bufferSize) {
            reallocate(bufferSize * 2);
        }

        length--;
        int item = buffer[idx];
        System.arraycopy(buffer, idx + 1, buffer, idx, length - idx);

        return item;
    }

    @Override
    public int get(int idx) {
        return buffer[idx];
    }

    @Override
    public int size() {
        return length;
    }
}
