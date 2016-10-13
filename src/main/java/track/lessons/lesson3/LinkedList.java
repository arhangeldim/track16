package track.lessons.lesson3;

public class LinkedList extends List implements Queue, Stack {

    private Node head;
    private Node tail;

    LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(int elem) {
        if (size == 0) {
            head = new Node();
            tail = head;
        } else {
            Node newNode = new Node();
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        ++size;
        tail.setValue(elem);
    }

    @Override
    public int remove(int index) {
        Node current = getNode(index);
        if (current == null) {
            return 0;
        }

        if (current.getPrev() != null) { // or !current.isEqual(head), но тогда нужно перегружать метод...
            current.getPrev().setNext(current.getNext());
        } else {
            head = current.getNext();
        }

        if (current.getNext() != null) { // or !current.isEqual(tail), но тогда нужно перегружать метод...
            current.getNext().setPrev(current.getPrev());
        } else {
            tail = current.getPrev();
        }

        --size;
        return current.getValue();
    }

    @Override
    public int get(int index) {
        Node current = getNode(index);
        if (current == null) {
            return 0;
        }

        return current.getValue();
    }

    protected Node getNode(int index) {
        if (!checkIndex(index)) {
            return null;
        }

        int i = 0;
        Node current = head;
        while(i++ != index) {
            current = current.getNext();
        }

        return current;
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }
}
