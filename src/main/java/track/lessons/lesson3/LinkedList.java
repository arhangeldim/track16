package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Queue, Stack {

    private class Node {
        int value;
        Node prev;
        Node next;

        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node begin = new Node(0, null, null);
    private Node end = begin;
    private int length = 0;

    private Node getNode(int idx) {
        Node item = begin.next;

        for (int i = 0; i < idx; ++i) {
            item = item.next;
        }

        return item;
    }

    @Override
    public void enqueue(int value) {
        length++;
        Node newBegin = new Node(0, null, begin);

        begin.prev = newBegin;
        begin.value = value;
        begin = newBegin;
    }

    @Override
    public int dequeue() {
        return pop();
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        if (length == 0) {
            printLengthError();
            return 0;
        }

        length--;
        int item = end.value;
        end.prev.next = null;
        end = end.prev;
        return item;
    }

    @Override
    public void add(int item) {
        length++;

        Node newEnd = new Node(item, end, null);
        end.next = newEnd;
        end = newEnd;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx >= length) {
            printLengthError();
            return 0;
        }

        length--;

        Node item = getNode(idx);
        item.prev.next = item.next;

        if (item.next != null) {
            item.next.prev = item.prev;
        }

        if (idx == length) {
            end = end.prev;
        }

        return item.value;
    }

    @Override
    public int get(int idx) {
        return getNode(idx).value;
    }

    @Override
    public int size() {
        return length;
    }
}
