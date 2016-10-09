package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {

    private Node head = null;

    @Override
    public void add(int item) {
        if (head == null) {
            head = new Node(item);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(item);
            current.next.prev = current;
        }
        size++;
    }

    @Override
    public int remove(int idx) {
        Node current = head;
        for (int i = 0; i < idx; i++) {
            current = current.next;
        }
        Node prev = current.prev;
        Node next = current.next;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        }
        size--;
        return current.value;
    }

    @Override
    public int get(int idx) {
        Node current = head;
        for (int i = 0; i < idx; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public void enqueue(int value) {
        this.add(value);
    }

    @Override
    public int dequeu() {
        return this.remove(0);
    }

    @Override
    public void push(int value) {
        this.add(value);
    }

    @Override
    public int pop() {
        return this.remove(size - 1);
    }

    private class Node {
        public Node next;
        public Node prev;
        public int value;

        public Node(int value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
}
