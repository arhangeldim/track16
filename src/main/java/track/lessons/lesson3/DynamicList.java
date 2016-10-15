package track.lessons.lesson3;


public class DynamicList extends List{
    private static final int DEFAULT_SIZE = 1000;
    private int[] array;
    private int size;

    public DynamicList() {
        array = new int[DEFAULT_SIZE];
    }

    @Override
    public void add(int value) {
        if(size == array.length) {
            allocate();
        }
        array[size++] = value;
    }

    @Override
    public int remove(int idx) {
        valid_index(idx);
        int value = array[idx];
        System.arraycopy(array, idx + 1, array, idx, size-- - idx);
        return value;
    }

    @Override
    public int get(int idx) {
        valid_index(idx);
        return array[idx];
    }
    @Override
    public int size() {
        return size;
    }

    private void allocate() {
        int[] newarray = new int[2 * size];
        System.arraycopy(array, 0, newarray, 0, size);
        array = newarray;
    }

    private void valid_index(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }
}