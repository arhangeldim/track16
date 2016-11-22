package track.lessons.lesson3;


public class DynamicList extends List {

    private int[] contents;
    private int length;
    public static final int DEFAULT_CAPACITY = 10;

    public DynamicList() {
        length = 0;
        contents = new int[DEFAULT_CAPACITY];
    }

    public DynamicList(int[] contents) {
        this.contents = contents;
        length = contents.length;
    }

    public int get(int idx) {
        if (idx < contents.length) {
            return contents[idx];
        } else {
            return -1;
        }
    }

    public int remove(int idx) {
        if (idx < contents.length) {
            int ret = contents[idx];
            shift(idx);
            length -= 1;
            return ret;
        } else {
            return -1;
        }
    }

    public void add(int item) {
        if (length < contents.length) {
            contents[length] = item;
            length += 1;
        } else {
            int[] old = new int[length + 1];
            System.arraycopy(contents, 0, old, 0, length);
            old[length] = item;
            length += 1;
            contents = old;
        }
    }

    private void shift(int idx) {
        System.arraycopy(contents, idx + 1, contents, idx, length - idx - 1);
    }
}
