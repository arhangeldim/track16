package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {
    public void push(int value) {
        add(value);
    }

    public int pop() throws Exception {
        if (begin == end) {
            throw new Exception();
        }
        Node endNode = end;
        endNode.prev.next = endNode.prev;
        end = endNode.prev;
        return endNode.value;
    }

    public void enqueue(int value) {
        add(value);
    }

    public int dequeue() throws Exception {
        return remove(0);
    }

    private class Node {
        private Node next;
        private Node prev;
        private int value;

        Node(int item) {
            value = item;
            next = this;
            prev = this;
        }
    }

    private Node begin = new Node(0);
    private Node end = begin;

    private Node getNode(int idx) throws Exception {
        if (idx < 0) {
            throw new Exception();
        }
        Node node = begin;
        for (int i = 0; i <= idx; ++i) {
            if (node == node.next) {
                throw new Exception();
            }
            node = node.next;
        }
        return node;
    }

    public void add(int item) {
        Node newNode = new Node(item);
        end.next = newNode;
        newNode.prev = end;
        end = newNode;
    }

    public int remove(int idx) throws Exception {
        Node node = getNode(idx);
        Node prevNode = node.prev;
        if (node == end) {
            prevNode.next = prevNode;
            end = prevNode;
        } else {
            Node nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        return node.value;
    }

    public int get(int idx) throws Exception {
        return getNode(idx).value;
    }

    public int size() {
        Node node = begin;
        int cnt = 0;
        while (node != node.next) {
            node = node.next;
            cnt++;
        }
        return cnt;
    }
}
