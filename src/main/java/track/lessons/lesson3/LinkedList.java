package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List implements Stack, Queue {

    class Node {
        private Node next;
        private Node prev;
        private int value;

        Node() {
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        Node(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        Node getNext() {
            return next;
        }

        Node getPrev() {
            return prev;
        }
    }


    Node root = null;
    private int size = 0;

    LinkedList() {
    }

    public LinkedList(int[] data) {
        root = new Node(data[0]);
        size++;
        Node prev = root;
        for (int i = 1; i < data.length; i++) {
            Node node = new Node(data[i]);
            node.setPrev(prev);
            prev.setNext(node);
            prev = node;
            size++;
        }
    }

    @Override
    public void add(int item) {
        Node node = new Node(item);
        if (root == null) {
            root = node;
        } else {
            Node current = root;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            node.setPrev(current);
            current.setNext(node);
        }
        size++;
    }

    private boolean isIndexCorrect(int idx) {
        return idx >= 0 && idx <= size;
    }

    @Override
    public int remove(int idx) {
        int value;
        Node current = root;
        if (!isIndexCorrect(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            for (int i = 0; i < idx; i++) {
                current = current.getNext();
            }
            value = current.getValue();
            if (idx == 0) {
                size--;
                root = current.getNext();
                if (root != null) {
                    root.setPrev(null);
                }
                return value;
            }
            if (idx == size - 1) {
                current.getPrev().setNext(null);
                size--;
                return value;
            }
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
            size--;
        }
        return value;
    }

    @Override
    public int get(int idx) {
        int value;
        Node current = root;
        if (!isIndexCorrect(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            for (int i = 0; i < idx; i++) {
                current = current.getNext();
            }
            value = current.getValue();
        }
        return value;
    }

    @Override
    public int size() {
        return size;
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
    public int dequeu() {
        remove(0);
        return 0;
    }

    public void dump() {
        if (root == null) {
            System.out.println("List is empty");
            return;
        }
        Node current = root;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
}
