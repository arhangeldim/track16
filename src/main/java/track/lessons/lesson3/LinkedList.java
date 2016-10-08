package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {

    private Node head;
    private Node tail;
    private int size;

    @Override
    void add(int item) {
        if (head == null) {
            head = tail = new Node(item, null, null);
        } else {
            Node node = new Node(item, tail, null);
            tail = node;
        }
        size++;
    }

    @Override
    int remove(int idx) {
        Node temp = getNode(idx);
        if (temp == null) {
            return -1;
        }
        temp.getPrev().changeNext(temp.getNext());
        temp.getNext().changePrev(temp.getPrev());
        size--;
        return temp.getValue();
    }

    Node getNode(int idx) {
        if (idx + 1 > size) {
            return null;
        } else {
            Node temp = head;
            for (int i = 0; i < idx; i++) {
                temp = temp.getNext();
            }
            return temp;
        }
    }

    @Override
    int get(int idx) {
        Node temp = getNode(idx);
        if (temp == null) {
            return -1;
        } else {
            return temp.getValue();
        }
    }

    @Override
    int size() {
        return size;
    }

    @Override
    public void enqueue(int value) {
        if (size == 0) {
            this.add(value);
        } else {
            Node newNode = new Node(value, tail, null);
            tail.changeNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public int dequeu() {
        if (size == 0) {
            return -1;
        }
        if (tail.getPrev() != null) {
            tail.getPrev().changeNext(null);
        }
        size--;
        int temp = tail.getValue();
        tail = tail.getPrev();
        return temp;
    }

    @Override
    public void push(int value) {
        if (size == 0) {
            this.add(value);
        } else {
            Node newNode = new Node(value, null, head);
            head.changePrev(newNode);
            head = newNode;
        }
        size++;
    }

    @Override
    public int pop() {
        if (size == 0) {
            return -1;
        }
        size--;
        if (head.getNext() != null) {
            head.getNext().changePrev(null);
        }
        int temp = head.getValue();
        head = head.getNext();
        return temp;
    }
}

class Node {
    private Node next;
    private Node prev;
    private int value;

    public Node(int value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public Node getPrev() {
        return prev;
    }

    public void changeNext(Node next) {
        this.next = next;
    }

    public void changePrev(Node prev) {
        this.prev = prev;
    }
}

interface Queue {

    void enqueue(int value); // поместить элемент в очередь

    int dequeu(); // вытащить первый элемент из очереди
}

interface Stack {
    void push(int value); // положить значение наверх стека

    int pop(); // вытащить верхнее значение со стека
}



