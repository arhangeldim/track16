package track.lessons.lesson3;



/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    private Node endNode;
    private Node headNode;

    @Override
    public void add(int item) {
        push(item);
        ++size;
    }

    @Override
    public int remove(int idx) {
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        Node currentNode = headNode;
        for (int i = 0; i != idx; ++i) {
            currentNode = currentNode.getNext();
        }
        int item = currentNode.getValue();
        currentNode.getPrev().setNext(currentNode.getNext());
        currentNode.getNext().setPrev(currentNode.getPrev());
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        Node currentNode = headNode;
        for (int i = 0; i != idx; ++i) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getValue();
    }

    @Override
    public void enqueue(int value) {
        Node newNode = new Node(value);
        if (headNode == null) {
            headNode = newNode;
            endNode = headNode;
        } else {
            endNode.setNext(newNode);
            newNode.setPrev(endNode);
            endNode = newNode;
        }
    }

    @Override
    public void push(int value) {
        Node newNode = new Node(value);
        if (headNode == null) {
            headNode = newNode;
            endNode = headNode;
        } else {
            endNode.setNext(newNode);
            newNode.setPrev(endNode);
            endNode = newNode;
        }
    }

    @Override
    public int dequeu() {
        if (headNode == null) {
            return -1;
        }
        int item = headNode.getValue();
        headNode = headNode.getNext();
        headNode.setPrev(null);
        return item;
    }

    @Override
    public int pop() {
        if (endNode == null) {
            return -1;
        }
        int item = endNode.getValue();
        endNode = endNode.getPrev();
        endNode.setNext(null);
        return item;
    }
}

