package track.lessons.lesson3;

/**
 *
 */
abstract class List {
    public abstract void add(int item);

    public abstract int remove(int idx);

    public abstract int get(int idx);

    public int size() {
        return size;
    }

    public abstract void printList();

    protected boolean checkIdx(int idx) {
        if (idx >= 0 && idx < size) {
            return true;
        }
        return false;

    }

    protected int size = 0;

}

