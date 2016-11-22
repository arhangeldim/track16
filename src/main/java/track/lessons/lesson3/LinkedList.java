package track.lessons.lesson3;

/**
 *
 */


public class LinkedList extends List
        implements Stack, Queue {

    private Node head;
    private Node tail;
    private int length;

    private class Node {

        private Node prev;
        private Node next;
        private int value;

        public Node() {
            setHead();
        }

        public Node(Node prev) {
            this.prev = prev;
        }

        public void setHead() {
            prev = null;
        }

        public void setTail() {
            next = null;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        private void setPrev(Node prev) {
            this.prev = prev;
        }

        private void setNext(Node next) {
            this.next = next;
        }

    }

    public LinkedList() {
        length = 0;
        head = new Node();
        tail = head;
    }

    public LinkedList(int[] data) {
        length = 0;
        head = new Node();
        Node current = head;
        Node prev;
        for (int i = 0; i < data.length; i++) {
            current.setValue(data[i]);
            length += 1;
            prev = current;
            current.next = new Node(prev);
            current = current.next;
        }
        tail = current.prev;
        tail.setTail();

    }

    @Override
    public void add(int item) {
        tail.setNext(new Node(tail));
        tail = tail.next;
        tail.setValue(item);
        tail.setTail();
        length += 1;
    }


    @Override
    public int remove(int idx) {
        int count = 0;
        Node current = head;
        while (count != idx) {
            if (current.next != null) {
                current = current.next;
                count += 1;
            } else {
                return -1; //some other error handling
            }
        }
        int ret = current.getValue();
        if (idx != 0) {
            current.prev.setNext(current.next);
        } else {
            head = current.next;
        }
        head.setHead();
        return ret;
    }


    @Override
    public int get(int idx) {
        int count = 0;
        Node current = head;
        while (count != idx) {
            if (current.next != null) {
                current = current.next;
                count += 1;
            } else {
                return -1;
            }
        }
        return current.getValue();
    }

    public int pop() {
        int ret = tail.getValue();
        remove(length - 1);
        return ret;
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        int ret = head.getValue();
        remove(0);
        return ret;
    }
}
