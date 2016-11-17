package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */
public class Node {

    private Node next;
    private Node prev;
    private int value;

    public Node(Node next, Node prev, int value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    public Node() {
        this(null,null,0);
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

