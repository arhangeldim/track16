package track.lessons.lesson3;

/**
 *
 */

public class LinkedList extends List {
    private Node head; // [head] <-> [index = 0] <-> [index = 1] <-> ... <-> [tail]
    private Node tail; // [Node]     [  Node   ]     [  Node   ]     ...     [Node]

    public LinkedList() {
        head = new Node(0);
        tail = new Node(0);
        size = 0;
    }

    public LinkedList(int initValue) {
        head = new Node(0);
        tail = new Node(0);
        Node newNode = new Node(initValue);
        head.setNext(newNode);
        tail.setPrev(newNode);
        newNode.setNext(tail);
        newNode.setPrev(head);
        size = 1;
    }

    public void add(int value) {
        size++;
        Node newNode = new Node(value);
        Node curNode = head;
        while (curNode.getNext() != tail) {
            curNode = curNode.getNext();
        }
        curNode.setNext(newNode);
        newNode.setPrev(curNode);
        tail.setPrev(newNode);
        newNode.setNext(tail);
    }

    public int remove(int index) {
        if (size == 0 || (index < 0 && index >= size - 1)) {
            System.out.println("bad index");
            return -1;
        }
        size--;
        Node curNode = head.getNext();
        for (int inc = 0; inc < index; inc++) {
            curNode = curNode.getNext();
        }
        int deleted = curNode.getValue();
        curNode.getPrev().setNext(curNode.getNext());
        curNode.getNext().setPrev(curNode.getPrev());
        return deleted;
    }

    public int get(int index) {
        if (size == 0 || (index < 0 && index >= size - 1)) {
            System.out.println("bad index");
            return -1;
        }
        Node curNode = head.getNext();
        for (int inc = 0; inc < index; inc++) {
            curNode = curNode.getNext();
        }
        return curNode.getValue();
    }
}
