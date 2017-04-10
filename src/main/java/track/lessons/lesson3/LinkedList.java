package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Queue, Stack {

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
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

    class Node {
        Node previous;
        Node next;
        int data;

        Node(int data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    LinkedList() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    @Override
    void add(int item) {
        if (size == 0) {
            first = new Node(item);
            last = first;
        } else if (size == 1) {
            last = new Node(item);
            first.next = last;
            last.previous = first;
        } else {
            Node toAdd = new Node(item);
            last.next = toAdd;
            toAdd.previous = last;
            last = toAdd;
        }
        size++;
    }

    @Override
    int remove(int idx) {
        int ans;
        if (idx > size) {
            throw new Error("Index in bigger than size!");
        } else if (size == 1) {
            ans = first.data;
            first = null;
            last = null;
        } else {
            Node toDel = first;
            for (int i = 0; i < idx; i++) {
                toDel = toDel.next;
            }
            ans = toDel.data;
            if (toDel.next != null) {
                toDel.next.previous = toDel.previous;
            } else {
                last = last.previous;
            }
            if (toDel.previous != null) {
                toDel.previous.next = toDel.next;
            } else {
                first = first.next;
            }
        }
        size--;
        return ans;
    }

    @Override
    int get(int idx) {
        if (idx > size) {
            throw new Error("Index is bigger than size!");
        } else {
            Node ans = first;
            for (int i = 0; i < idx; i++) {
                ans = ans.next;
            }
            return ans.data;
        }
    }

    @Override
    int size() {
        return size;
    }

    private Node first;
    private Node last;
    private int size;
}
