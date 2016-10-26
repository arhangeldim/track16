package track.lessons.lesson3;

/**
 * Created by Konstantin on 13.10.2016.
 */
public class LinkedList extends List implements Stack, Queue {

    private Node first;
    private Node last;

    public LinkedList() {
        first = null;
        last = null;
        size = 0;

    }

    public Node getNode(int idx) {
        checkIndex(idx);

        Node element;

        if (idx < size / 2) {
            element = first;
            for (int i = 0; i < idx; i++) {
                element = element.getNext();
            }
        } else {
            element = last;
            for (int i = size - 1; i > idx; i--) {
                element = element.getPrev();
            }
        }

        return element;
    }

    @Override
    public void add(int item) {
        Node element = new Node(null, null, item);

        if (size == 0) {
            first = element;
            last = element;
            size++;
        } else {
            element.setPrev(last);
            last.setNext(element);
            last = element;
            size++;
        }
    }

    @Override
    public int remove(int idx) {

        Node removeElement = getNode(idx);

        Node nextElement = removeElement.getNext();
        Node prevElement = removeElement.getPrev();

        if (nextElement == null) {
            if (prevElement == null) {
                first = null;
                last = null;
            } else {
                last = prevElement;
                prevElement.setNext(null);
            }
        } else if (prevElement == null) {
            first = nextElement;
            nextElement.setPrev(null);
        } else {
            nextElement.setPrev(prevElement);
            prevElement.setNext(nextElement);
        }
        removeElement.setNext(null);
        removeElement.setPrev(null);
        size--;

        return removeElement.getValue();
    }

    @Override
    public int get(int idx) {
        return getNode(idx).getValue();
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        return remove(0);
    }
}
