package track.lessons.lesson3;

import java.util.Arrays;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {

    private class Node {
        public Node prev;
        public Node next;
        public int item;
    }

    private Node first;
    private Node last;
    private int size;

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void add(int item) {
        if (isEmpty()) {
            first = last = new Node();
            first.item = item;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.prev = oldLast;
            last.next = null;
            oldLast.next = last;
        }
        size++;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (idx == 0) {
            return removeFirst();
        }

        if (idx == size - 1) {
            return removeLast();
        }

        Node current = first;
        for (int id = 0; id < idx; id++) {
            current = current.next;
        }

        Node prev = current.prev;
        Node next = current.next;
        final int item = current.item;
        prev.next = next;
        next.prev = prev;
        size--;
        return item;
    }

    private int removeLast() {
        final int item = last.item;
        last = last.prev != null ? last.prev : null;
        if (last != null) {
            last.next = null;
        }
        size--;
        return item;
    }

    private int removeFirst() {
        final int item = first.item;
        first = first.next != null ? first.next : null;
        if (first != null) {
            first.prev = null;
        }
        size--;
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (idx == 0) {
            return first.item;
        }

        if (idx == size - 1) {
            return last.item;
        }

        Node current = first;
        for (int id = 0; id < idx; id++) {
            current = current.next;
        }

        return current.item;
    }

    @Override
    public int size() {
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

    private boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        int[] elems = new int[size];
        Node current = first;
        for (int i = 0; i < size; i++) {
            elems[i] = current.item;
            current = current.next;
        }
        return "LinkedList{" +
                "size=" + size +
                ", elems=" + Arrays.toString(elems) +
                '}';
    }
}
