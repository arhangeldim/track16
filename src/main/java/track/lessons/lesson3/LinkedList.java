package track.lessons.lesson3;


public class LinkedList extends List implements Stack, Queue {

    private class Node {
        public Node next;
        public Node prev;
        public int value;
    }

    private Node first;
    private Node last;
    private int size;

    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    private boolean isEmpty() {
        if (size > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void add(int item) {
        Node newNode = new Node();
        if (isEmpty() == true) {
            newNode.prev = newNode;
            newNode.next = newNode;
            first = newNode;
        } else {
            newNode.prev = last;
            newNode.next = first;
        }
        last = newNode;
        first.prev  = last;
        newNode.value = item;
        size++;
    }

    private void valid_index(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public int remove(int idx) {
        valid_index(idx);
        size--;
        if (idx == 0) {
            return removeFirst();
        }
        if (idx == size) {
            return removeLast();
        }
        Node node = new Node();
        node = first;
        for (int i = 0; i <= idx; i++) {
            node = node.next;
        }
        int cur_value = node.value;
        Node next = new Node();
        next = node.next;
        node.prev.next = node.next;
        next.prev = node.prev;
        return cur_value;
    }

    private int removeLast() {
        int value = last.value;
        if (last.prev != null) {
            last = last.prev;
            last.prev.next = null;
        } else {
            last = null;
        }
        return value;
    }

    private int removeFirst() {
        int value = first.value;
        if(first.next != null) {
            first = first.next;
            first.next.prev = null;
        } else {
            first = null;
        }
        return value;
    }

    @Override
    int get(int idx) {
        valid_index(idx);
        if (idx == 0) {
            return first.value;
        }
        if (idx == size - 1) {
            return last.value;
        }
        Node node = new Node();
        node = first;
        for (int i = 0; i <= idx; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    int size(){
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