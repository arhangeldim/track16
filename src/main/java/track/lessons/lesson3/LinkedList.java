package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List {

    private Node head;
    private Node tail;

    private int size;

    LinkedList() {
        super();
    }

    @Override
    public void add(int item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        ++size;
    }

    @Override
    public int remove(int idx) throws IndexOutOfBoundsException {
        Node node = getNode(idx);
        if (node != null) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                if (node.equals(head)) {
                    head = node.getNext();
                    head.setPrev(null);
                } else if (node.equals(tail)) {
                    tail = node.getPrev();
                    tail.setNext(null);
                } else {
                    node.getPrev().setNext(node.getNext());
                    node.getNext().setPrev(node.getPrev());
                }
            }
            --size;
            return node.getValue();
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int get(int idx) throws IndexOutOfBoundsException {
        Node node = getNode(idx);
        return node.getValue();
    }

    public Node getNode(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 | idx >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node curNode = head;
        for (int i = 0; i < idx; i++) {
            curNode = curNode.getNext();
        }
        return curNode;
    }

    @Override
    public int size() {
        return size;
    }

}
