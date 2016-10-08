package track.lessons.lesson3;

public class Node {
    private Node next;
    private Node prev;
    private int value;

    public Node(int value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public Node getPrev() {
        return prev;
    }

    public void changeNext(Node next) {
        this.next = next;
    }

    public void changePrev(Node prev) {
        this.prev = prev;
    }
}
