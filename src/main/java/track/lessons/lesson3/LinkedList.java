package track.lessons.lesson3;

/**
 *
 */


public class LinkedList extends List implements Queue, Stack {

    @Override
    public void push(int item) {
        this.add(item);
    }

    @Override
    public int pop() {
        return this.remove(this.size - 1);
    }

    @Override
    public void enqueue(int item) {
        this.add(item);
    }

    @Override
    public int dequeue() {
        return this.remove(0);
    }

    private class Node {
        private Node next;
        private Node prev;
        private int value;

        public Node(Node next, Node prev, int value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public Node() {
        }
    }

    private Node start;
    private Node end;

    public LinkedList() {
        this.start = null;
        this.end = null;
        this.size = 0;
    }

    @Override
    void add(int item) {
        if (isEmpty()) {
            Node newNode = new Node(null, null, item);
            this.start = newNode;
            this.end = newNode;
        } else {
            Node newNode = new Node(null, this.end, item);
            this.end.next = newNode;
            this.end = newNode;
        }
        this.size++;

    }

    private boolean isEmpty() {
        if (this.size > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    int remove(int idx) {
        isValid(idx);
        if (this.size == 1) {
            int currvalue = this.start.value;
            this.start = null;
            this.end = null;
            this.size--;
            return currvalue;
        } else if (idx == 0) {
            return removeStart();
        } else if (idx == this.size - 1) {
            return removeEnd();
        } else {
            Node iter = new Node();
            iter = this.start;
            for (int i = 0; i < idx; i++) {
                iter = iter.next;
            }
            int currvalue = iter.value;
            Node nextForDeleted = new Node();
            nextForDeleted = iter.next;
            iter.prev.next = nextForDeleted.next;
            nextForDeleted.prev = iter.prev;
            this.size--;
            return currvalue;
        }
    }

    private int removeEnd() {
        int currvalue = this.end.value;
        if (this.end.prev != null) {
            this.end = this.end.prev;
            this.end.next = null;
        } else {
            this.end = null;
        }
        this.size--;
        return currvalue;
    }

    private int removeStart() {
        int currvalue = this.start.value;
        if (!(this.start.next == null)) {
            this.start = this.start.next;
            this.start.prev = null;
        } else {
            this.start = null;
        }
        this.size--;
        return currvalue;
    }

    @Override
    int get(int idx) {
        isValid(idx);
        if (idx == 0) {
            return this.start.value;
        } else if (idx == size - 1) {
            return this.end.value;
        } else {
            Node iter = new Node();
            iter = this.start;
            for (int i = 0; i < idx; i++) {
                iter = iter.next;
            }
            return iter.value;
        }
    }


    @Override
    int size() {
        return super.size();
    }

    @Override
    void print() {
        if (this.size == 0) {
            System.out.print("List is empty\n");
        }
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.get(i));
        }
    }

    @Override
    boolean isValid(int idx) {
        return super.isValid(idx);
    }
}
