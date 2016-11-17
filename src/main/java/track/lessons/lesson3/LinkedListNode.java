package track.lessons.lesson3;

/**
 * Created by hellishnoob on 13.10.16.
 */
public class LinkedListNode {
    private int value;
    private LinkedListNode previous;
    private LinkedListNode next;

    public LinkedListNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LinkedListNode getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedListNode previous) {
        this.previous = previous;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }
}
