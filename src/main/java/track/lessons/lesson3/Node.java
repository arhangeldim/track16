package track.lessons.lesson3;

public class Node {

    private Node next;
    private Node prev;
    private int value;

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    int getValue() {
        return value;
    }

    public void setNext(Node nextNode) {
        this.next = nextNode;
    }

    public void setPrev(Node prevNode) {
        this.prev = prevNode;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
