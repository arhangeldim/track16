package track.lessons.lesson3;

import org.mockito.internal.matchers.Equals;
import org.omg.CORBA.Object;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
/**
 *
 */

public class LinkedList extends List implements Stack, Queue {

    private class Node {
        private Node next;
        private Node prev;

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public int getValue() {
            return value;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void setValue(int value) {
            this.value = value;
        }

        private int value;

        protected Node() {
            value = 0;
            next = null;
            prev = null;
        }

        protected Node(int val) {
            value = val;
            next = null;
            prev = null;
        }

        protected Node(int val, Node nextNew, Node prevNew) {
            value = val;
            next = nextNew;
            prev = prevNew;
        }
    }

    private Node begin = new Node();
    private Node end = begin;
    private int size = 0;

    void add(int item) {
        if (size == 0) {
            begin.setValue((item));
        } else {
            Node newNode = new Node(item, null, end);
            end.setNext(newNode);
            end = newNode;
        }
        size++;
    }

    int remove(int idx) {
        if (idx < 0 || idx > size) {
            System.err.print("Bad index!");
            return 0;
        }
        Node curr = begin;
        if (idx == size - 1 && !begin.equals(end)) {
            end = end.getPrev();
        }
        if (idx == 0 && !begin.equals(end)) {
            begin = begin.getNext();
        }
        size--;
        for (int i = 0; i < idx; i++) {
            curr = curr.getNext();
        }
        if (curr.getPrev() != null) {
            curr.getPrev().setNext(curr.getNext());
        }
        if (curr.getNext() != null) {
            curr.getNext().setPrev(curr.getPrev());
        }
        return curr.getValue();
    }

    int get(int idx) {
        if (idx < 0 || idx > size) {
            System.err.print("Bad index!");
            return 0;
        }
        Node curr = begin;
        for (int i = 0; i < idx; i++) {
            curr = curr.getNext();
        }
        return curr.getValue();
    }

    int size() {
        return size;
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeu() {
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