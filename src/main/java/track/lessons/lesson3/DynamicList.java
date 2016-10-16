package track.lessons.lesson3;

/**
 *
 */
public class DynamicList extends List {
    private static final int defaultCap = 10;
    private int [] data;

    public DynamicList() {
        data = new int[defaultCap];
        size = 0;
    }

    public DynamicList(int initValue) {
        data = new int[defaultCap];
        size = 1;
        data[0] = initValue;
    }

    private void extend() {
        int [] newData = new int[data.length * 2];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    public void add(int value) {
        if (size == data.length) {
            extend();
        }
        data[size] = value;
        size++;
    }

    public int remove(int index) {
        if (size == 0 || (index < 0 && index >= size - 1)) {
            System.out.println("bad index");
            return -1;
        }
        int deleted = data[index];
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
        size--;
        return deleted;
    }

    public int get(int index) {
        if (size == 0 || (index < 0 && index >= size - 1)) {
            System.out.println("bad index");
            return -1;
        }
        return data[index];
    }
}
