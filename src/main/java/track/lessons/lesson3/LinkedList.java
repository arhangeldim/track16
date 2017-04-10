package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Queue,Stack {

    private Node head;
    private Node tail;
    private int countOfItems = 0;

    public LinkedList() {
    }


    public void add(int item) {
        Node addNode = new Node(item, null, null);
        if (head == null) {
            head = addNode;
            tail = head;
        } else {
            addNode.setPrev(tail);
            tail.setNext(addNode);
            tail = addNode;
        }
        countOfItems++;
    }

    public int remove(int idx) {
        Node currNode = head;
        for (int i = 0; i < idx; i++) {
            currNode = currNode.getNext();
        }

        Node prevNode = currNode.getPrev();
        Node nextNode = currNode.getNext();
        final int result = currNode.getValue();
        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = currNode.getNext();
        }
        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = currNode.getPrev();
        }

        countOfItems--;
        return result;
    }

    public int get(int idx) {
        Node currNode = head;
        for (int i = 0; i < idx; i++) {
            currNode = currNode.getNext();
        }
        return currNode.getValue();
    }

    public int size() {
        return countOfItems;
    }

    @Override
    public void enqueue(int value) {
        this.add(value);
    }

    @Override
    public int dequeu() {
        return this.remove(0);
    }

    @Override
    public void push(int value) {
        this.add(value);
    }

    @Override
    public int pop() {
        return this.remove(countOfItems);
    }
}
