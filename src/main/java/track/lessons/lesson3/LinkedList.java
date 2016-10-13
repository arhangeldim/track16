package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    private LinkedListNode first;
    private LinkedListNode last;
    private int size;

    @Override
    public void add(int item) {
        if (first == null) {
            first = new LinkedListNode(item);
            last = first;
            size++;
        } else {
            last.setNext(new LinkedListNode(item));
            last.getNext().setPrevious(last);
            last = last.getNext();
            size++;
        }
    }

    @Override
    public int remove(int idx) {
        int value;
        if (isWithinRange(idx)) {
            LinkedListNode current = first;
            for (int i = 0; i < idx; i++) {
                current = current.getNext();
            }
            LinkedListNode previous = current.getPrevious();
            LinkedListNode next = current.getNext();
            if (previous != null) {
                previous.setNext(next);
            }
            if (next != null) {
                next.setPrevious(previous);
            }
            if (current == first) {
                first = first.getNext();
            }
            if (current == last) {
                last = last.getPrevious();
            }
            value = current.getValue();
            size--;
        } else {
            value = INVALID_VALUE;
        }
        return value;
    }

    @Override
    public int get(int idx) {
        int value;
        if (isWithinRange(idx)) {
            LinkedListNode current = first;
            for (int i = 0; i < idx; i++) {
                current = current.getNext();
            }
            value = current.getValue();
        } else {
            value = INVALID_VALUE;
        }
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @param idx The index of the item to check if it is within boundaries of the list
     * @return true if the index idx is within boundaries of the list, false otherwise
     */
    private boolean isWithinRange(int idx) {
        return (idx >= 0) && (idx < size);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeu() {
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }
}
