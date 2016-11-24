package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {

    //Начало списка
    private Node head = null;

    //Конец списка
    private Node tail = null;

    @Override
    void add(int item) {
        Node node = new Node(tail, null, item);
        if (head == null) {
            head = node;
        }
        if (tail != null) {
            tail.setNext(node);
        }
        tail = node;
        size++;
    }

    Node getNode(int idx) {
        validateIndex(idx);
        Node node = head;
        if (node == null) {
            return null;
        }
        for (int i = 0; i < idx && node.hasNext(); i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    int remove(int idx) {
        //validateIndex(idx);

        Node node = getNode(idx);
        if (node == null) {
            throw new UnknownError("Element not found =(");
        }
        Node prev = node.prev;
        Node next = node.next;
        if (prev != null) {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }
        size--;
        if (idx == 0) {
            head = next;
        }
        if (idx == size) {
            tail = prev;
        }
        return node.getValue();
    }

    @Override
    int get(int idx) {
        Node node = getNode(idx);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return node.getValue();
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
    public int dequeu() {
        Node node = head;
        if (head != null) {
            head = head.getNext();
            head.setPrev(null);
        }
        return node.getValue();
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        Node node = tail;
        if (tail != null) {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        return node.getValue();
    }

    class Node {
        private Node next;
        private Node prev;
        private int value;

        public Node(Node prev, Node next, int value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
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

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean hasNext() {
            return next != null;
        }
    }


}
