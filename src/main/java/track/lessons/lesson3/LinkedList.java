package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Queue, Stack {
    private class Node {
        private Node next;
        private Node prev;
        private int value;

        public Node(Node prev, int value) {
            next = null;
            this.prev = prev;
            this.value = value;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public int getValue() {
            return value;
        }
    }

    Node content;
    int size;

    void add(int item) {
        if (content == null) {
            content = new Node(null, item);
        } else {
            Node tmp = content;
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(new Node(null, item));
        }
        size++;
    }

    int remove(int idx) {
        Node numberN = content;
        if (idx < 0) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        for (int i = 0; i < idx; i++) {
            numberN = numberN.getNext();
            if (numberN == null) {
                System.out.println("This list doesn't have element with this index.");
                return -1;
            }
        }
        if (numberN.prev != null) {
            numberN.prev.setNext(numberN.next);
        }
        if (numberN.next != null) {
            numberN.next.setPrev(numberN.prev);
        }
        if (idx == 0) {
            content = content.next;
        }
        size--;
        return numberN.value;
    }

    int get(int idx) {
        if (idx < 0) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        Node numberN = content;
        for (int i = 0; i < idx; i++) {
            numberN = numberN.getNext();
            if (numberN == null) {
                System.out.println("This list doesn't have element with this index.");
                return -1;
            }
        }
        return numberN.getValue();
    }

    int size() {
        return size;
    }

    public void enqueue(int value) {
        this.add(value);
    }

    public int dequeu() {
        return this.remove(0);
    }

    public void push(int value) {
        this.add(value);
    }

    public int pop() {
        return this.remove(this.size() - 1);
    }
}
