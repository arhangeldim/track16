package track.lessons.lesson3;

<<<<<<< HEAD
import org.mockito.internal.matchers.Null;

/**
 * Created by frystile on 13.10.16.
 */

class Node {
    Node next;
    Node prev;
    int value;

    Node(Node next, Node prev, int value) {
        this.next = next;
        this.prev = prev;
        this.value = value;
    }
}

class LinkedList extends List implements Stack, Queue {
    private Node first;
    private Node last;
    private int size;

    LinkedList() {
        first = new Node(null, null, -1);
        first.next = first;
        last = first;
        size = 0;
    }

    @Override
    void add(int item) {
        Node temp = new Node(null, last, item);
        last.next = temp;
        last = temp;
        ++size;
    }

    @Override
    int remove(int idx) {
        if (idx >= size || idx < 0) {
            System.err.println("Incorrect index");
            return 0;
        }

        Node search = first;
        while (idx >= 0) {
            search = search.next;
            --idx;
        }

        search.prev.next = search.next;
        if (search.next == null) {
            last = search.prev;
            if (first.next == null) {
                first.next = last;
            }
        } else {
            search.next.prev = search.prev;
        }

        --size;
        return search.value;
    }

    @Override
    int get(int idx) {
        if (idx >= size || idx < 0) {
            System.err.println("Incorrect index");
            return 0;
        }

        Node search = first;
        while (idx >= 0) {
            search = search.next;
            --idx;
        }

        return search.value;
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
        if (size == 0) {
            System.err.println("Incorrect method");
            return 0;
        }
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        if (size == 0) {
            System.err.println("Incorrect method");
            return 0;
        }
        return remove(size - 1);
    }
=======
/**
 *
 */
public class LinkedList {
>>>>>>> arch/master
}
