package track.lessons.lesson3;

/**
 *
 */
public abstract class List {
    protected void printLengthError() {
        System.err.println("List index out of range");
    }

    public abstract void add(int item);

    public abstract int remove(int idx);

    public abstract int get(int idx);

    public abstract int size();
}
