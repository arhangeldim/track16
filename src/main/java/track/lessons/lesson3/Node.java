package track.lessons.lesson3;

/**
 * Created by les on 10/9/16.
 */

public class Node {
    private Node next;
    private Node prev;
    private int value;

    public Node(int value) {
        this.value = value;
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
