package track.lessons.lesson3;

/**
 * Created by mif-a on 08.10.2016.
 */
public class Node {
    private Node next;
    private Node prev;
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getPrev() {
        return prev;
    }

    public Node getNext() {
        return next;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPrev(Node prev) {

        this.prev = prev;
    }

    public void setNext(Node next) {

        this.next = next;
    }
}
