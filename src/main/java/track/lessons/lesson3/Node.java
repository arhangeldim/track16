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

    public Node(Node node) {
        prev = node;
        next = null;
    }

    public Node append(int value) {
        Node newNode = new Node(value);
        if (next != null) {
            next.prev = newNode;
        }
        newNode.next = next;
        newNode.prev = this;
        next = newNode;
        return newNode;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (prev != null) {
            prev.next = newNode;
        }
        newNode.prev = prev;
        newNode.next = this;
        prev = newNode;
    }

    public int remove() {
        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = prev;
        }
        return value;
    }

    public int getValue() {
        return value;
    }

    public Node next() {
        return next;
    }

    public Node prev() {
        return prev;
    }
}
