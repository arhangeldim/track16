package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
class DynamicList<T> extends List<T> {

    private Object[] data;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicList() {
        size = 0;
        capacity = DEFAULT_CAPACITY;
        data = new Object[capacity];
    }

    public DynamicList(int size) {
        this.size = size;
        capacity = size + (size >> 1);
        data = new Object[capacity];
    }

    @Override
    public void add(T item) {
        if (size < capacity) {
            data[size] = item;
            size++;
        } else {
            capacity += (capacity >> 1);
            Object[] dataExtended = new Object[capacity];
            System.arraycopy(data, 0, dataExtended, 0, size);
            data = dataExtended;
            data[size] = item;
            size++;
        }
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            System.out.println("Error!");
            return null;
        }


        if (size < capacity >> 2) {
            capacity = size + (size >> 1);
            Object[] dataTrimmed = new Object[capacity];
            System.arraycopy(data, 0, dataTrimmed, 0, size);
            data = dataTrimmed;
        }

        T removedItem = (T) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[size--] = null;
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            System.out.println("Error!");
            return null;
        }
        return (T) data[index];
    }
}