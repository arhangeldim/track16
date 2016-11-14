package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    protected int size = 0;

    public abstract void add(int item);

    public abstract int remove(int idx);

    public abstract int get(int idx);

    public abstract void printList();

    public int size() {
        return size;
    }

    protected boolean checkIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        return false;
    }
}
