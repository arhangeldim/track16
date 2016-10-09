package track.lessons.lesson3;

import java.util.EmptyStackException;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    private class Node {
        Node next;
        Node prev;
        final int value;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private int size;

    LinkedList() {
        size = 0;
    }

    @Override
    void add(int item) {
        size++;
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            return;
        }
        Node cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        newNode.prev = cur;
        cur.next = newNode;

    }

    @Override
    int remove(int idx) {
        if (idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node cur = head;
        for (int i = 0; i < idx; i++) {
            cur = cur.next;
        }
        if (cur == head) {
            head = cur.next;
        } else {
            cur.prev.next = cur.next;
        }
        if (cur.next != null) {
            cur.next.prev = cur.prev;
        }
        size--;
        return cur.value;
    }

    @Override
    int get(int idx) {
        if (idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node cur = head;
        for (int i = 0; i < idx; i++) {
            cur = cur.next;
        }
        return cur.value;
    }

    @Override
    int size() {
        return size;
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        Node first = head;
        if (first == null) {
            throw new EmptyStackException();
        }
        head = head.next;
        size--;
        return first.value;
    }

    @Override
    public void push(int value) {
        size++;
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            return;
        }
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    @Override
    public int pop() {
        return this.dequeue();
    }


}
