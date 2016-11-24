package track.lessons.lesson3;

/**
 * Created by mannimarco on 11/10/2016.
 */
public class Node {
    private Node prev;
    private Node next;
    private int value;


    public Node(int value) {
        this.value = value;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}