package track.lessons.lesson3;

import java.util.EmptyStackException;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    LinkedList() {
        size = 0;
        head = tail = null;
    }

    @Override
    public void add(int item) throws Exception {
        Node newnode = new Node(item, tail, head);
        if (size > 0) {
            tail.setNext(newnode);
            head.setPrev(newnode);
        } else {
            tail = head = newnode;
        }
    }

    @Override
    public int remove(int idx) throws Exception {
        if (idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (size == 1) {
            head = tail = null;
            return head.getValue();
        } else {
            Node todel = find(idx);
            Node from = todel.getPrev();
            Node to = todel.getNext();
            from.setNext(to);
            to.setPrev(from);
            return todel.getValue();
        }
    }

    @Override
    public int get(int idx) throws Exception {
        if (idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return find(idx).getValue();
    }

    private Node find(int idx) {
        Node res = head;
        if (idx <= size / 2) {
            int i = 0;
            while (i < idx) {
                res = res.getNext();
                i++;
            }
        } else {
            int i = size - 1;
            while (i > idx) {
                res = res.getNext();
                i--;
            }
        }

        return res;
    }

    private Node head;
    private Node tail;

    @Override
    public void push(int value) throws Exception {
        add(value);
    }

    @Override
    public int pop() throws Exception {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return remove(size - 1);
    }

    @Override
    public void enqueue(int value) throws Exception {
        add(value);
    }

    @Override
    public int dequeue() throws Exception {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return remove(0);
    }
}

class Node {
    private Node next;
    private Node prev;
    private int value;

    Node(int val, Node from, Node to) {
        value = val;
        prev = from;
        next = to;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public int getValue() {
        return value;
    }
}
