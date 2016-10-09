package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
class DynamicList<T> extends List<T> {

    private Object[] data;
    private int capacity;

    DynamicList() {
        size = 0;
        capacity = 10;
        data = new Object[capacity];
    }

    DynamicList(int size) {
        this.size = size;
        this.capacity = (int) ((double) size * 1.5);
        data = new Object[capacity];
    }

    @Override
    void add(T item) {
        if (size < capacity) {
            data[size] = item;
            size++;
        } else {
            capacity = (int) ((double) capacity * 1.5);
            Object[] dataExtended = new Object[capacity];
            System.arraycopy(data, 0, dataExtended, 0, size);
            data = dataExtended;
            data[size] = item;
            size++;
        }
    }

    @Override
    T remove(int index) {
        if (index >= size || index < 0) {
            System.out.println("Error!");
            return null;
        }

        T removedItem = (T) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return removedItem;
    }

    @Override
    T get(int index) {
        if (index >= size || index < 0) {
            System.out.println("Error!");
            return null;
        }
        return (T) data[index];
    }
}