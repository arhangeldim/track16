package track.lessons.lesson3;



/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    private Node tailNode;
    private Node headNode;

    @Override
    public void add(int item) {
        push(item);
        ++size;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0) {
            System.out.println("The index can't be less than 0");
            return -1;
        }
        if (idx > size - 1) {
            System.out.println("Index out of range");
            return -1;
        }
        Node currentNode = headNode;
        for (int i = 0; i != idx; ++i) {
            currentNode = currentNode.getNext();
        }
        size--;
        int item = currentNode.getValue();
        if (currentNode.getPrev() != null) {
            currentNode.getPrev().setNext(currentNode.getNext());
        } else {
            headNode = currentNode.getNext();
        }
        if (currentNode.getNext() != null) {
            currentNode.getNext().setPrev(currentNode.getPrev());
        }
        return item;
    }

    @Override
    public int get(int idx) {
        if (idx < 0) {
            System.out.println("The index can't be less than 0");
            return -1;
        }
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
        push(value);
    }

    @Override
    public void push(int value) {
        Node newNode = new Node(value);
        if (headNode == null) {
            headNode = newNode;
            tailNode = headNode;
        } else {
            tailNode.setNext(newNode);
            newNode.setPrev(tailNode);
            tailNode = newNode;
        }
    }

    @Override
    public int dequeu() {
        return remove(0);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }
}

