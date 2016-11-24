package track.lessons.lesson3;

abstract class List {

    protected int size;

    public int size() {
        return size;
    }

    public abstract void add(int elem);

    public abstract int remove(int index);

    public abstract int get(int index);

    protected boolean checkIndex(int index) {
        if ((index < 0) || (index >= size)) {
            System.err.print("Error: out of borders\n");
            return false; // Null не ставится.
        } else {
            return true;
        }
    }
}
