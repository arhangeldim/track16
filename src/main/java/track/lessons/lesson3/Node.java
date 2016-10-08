package track.lessons.lesson3;

/**
 * Created by oleg on 08.10.16.
 */
public class Node {
    private Node next;
    private Node prev;
    private int value;

    Node(int value) {
        this.value = value;
    }

    Node(Node node) {
        prev = node;
        next = null;
    }

    public Node append(int value) {
        Node newNode = new Node(value);
        if (this.next != null) {
            this.next.prev = newNode;
        }
        newNode.next = this.next;
        newNode.prev = this;
        this.next = newNode;
        return newNode;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (this.prev != null) {
            this.prev.next = newNode;
        }
        newNode.prev = this.prev;
        newNode.next = this;
        this.prev = newNode;
    }

    public int remove() {
        if (this.prev != null) {
            this.prev.next = this.next;
        }
        if (this.next != null) {
            this.next.prev = this.prev;
        }
        return value;
    }

    public int get() {
        return value;
    }

    public Node next() {
        return next;
    }

    public Node prev() {
        return prev;
    }
}
