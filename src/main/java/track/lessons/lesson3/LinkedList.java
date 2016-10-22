package track.lessons.lesson3;

import java.text.MessageFormat;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    public void push(int value) {
        add(value);
    }

    public int pop() throws IndexOutOfRangeException {
        size--;
        if (end == null) {
            throw new IndexOutOfRangeException("You want to pop from the empty list");
        }
        Node endNode = end;
        if (begin == end) {
            begin = null;
        } else{
            endNode.prev.next = null;
        }
        end = endNode.prev;
        return endNode.value;
    }

    public void enqueue(int value) {
        add(value);
    }

    public int dequeue() throws IndexOutOfRangeException {
        return remove(0);
    }

    private class Node {
        private Node next;
        private Node prev;
        private int value;

        Node(int item) {
            value = item;
            next = null;
            prev = null;
        }
    }

    private Node begin = null;
    private Node end = null;
    private int size = 0;

    private Node getNode(int idx, String action) throws IndexOutOfRangeException {
        if (0 > idx || idx >= size) {
            throw new IndexOutOfRangeException(MessageFormat.format("You want to {0} {1} element from" +
                            " dynamic list with {2} elements.", action, Integer.toString(idx),
                            Integer.toString(size)));
        }
        Node node = begin;
        for (int i = 0; i < idx; ++i) {
            node = node.next;
        }
        return node;
    }

    public void add(int item) {
        size++;
        Node newNode = new Node(item);
        if (end == null) {
            begin = newNode;
            end = newNode;
        } else {
            end.next = newNode;
            newNode.prev = end;
            end = newNode;
        }
    }

    public int remove(int idx) throws IndexOutOfRangeException {
        Node node = getNode(idx, "remove");
        size--;
        if (node == begin && node == end) {
            begin = null;
            end = null;
        } else if (node == begin) {
            node.next.prev = null;
            begin = node.next;
        } else if (node == end) {
            node.prev.next = null;
            end = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        return node.value;
    }

    public int get(int idx) throws IndexOutOfRangeException {
        return getNode(idx, "get").value;
    }

    public int size() {
        return size;
    }
}
