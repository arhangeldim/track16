package track.lessons.lesson3;

public class Node {
    private int value;
    private Node next;
    private Node prev;

    public Node(int initValue) {
        next = null;
        prev = null;
        value = initValue;
    }

    int getValue() {
        return value;
    }

    void setPrev(Node newPrev) {
        prev = newPrev;
    }

    Node getPrev() {
        return prev;
    }

    void setNext(Node newNext) {
        next = newNext;
    }

    Node getNext() {
        return next;
    }
}