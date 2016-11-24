package track.lessons.lesson3;

public class LinkedList extends List implements Queue, Stack {

    class Node {
        private Node next;
        private Node prev;
        private int value;

        public Node() {
            next = null;
            prev = null;
            value = 0;
        }

        public void setNext(Node node) {
            this.next = node;
        }

        public void setPrev(Node node) {
            this.prev = node;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return this.next;
        }

        public Node getPrev() {
            return this.prev;
        }

        public int getValue() {
            return this.value;
        }
    }

    private int size;
    private Node root;

    public LinkedList() {
        root = null;
        size = 0;
    }

    @Override
    public void add(int item) {
        Node newnode = new Node();
        newnode.setValue(item);
        if (size == 0) {
            root = newnode;
        } else {
            Node search = root;
            while (search.getNext() != null) {
                search = search.getNext();
            }
            search.setNext(newnode);
            newnode.setPrev(search);
        }
        size++;
    }

    @Override
    public int remove(int idx) {
        if (index(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            if (idx == 0) {
                root = root.getNext();
                size--;
                return root.getValue();
            }
            Node delete = root;
            int item = 0;
            while (item != idx) {
                delete = delete.getNext();
                item = item + 1;
            }
            if (idx != size - 1) {
                delete.getPrev().setNext(delete.getNext());
                delete.getNext().setPrev(delete.getPrev());
            } else {
                delete.getPrev().setNext(null);
            }
            size--;
            return delete.getValue();
        }
    }

    private boolean index(int idx) {
        return idx < 0 || idx > size - 1;
    }

    @Override
    public int get(int idx) {
        if (index(idx)) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            Node search = root;
            int item = 0;
            while (item != idx) {
                search = search.getNext();
                item++;
            }
            return search.getValue();
        }
    }

    @Override
    public int size() {
        return size;
    }


    public void push(int value) {
        add(value);
    }


    public int pop() {
        return remove(size - 1);
    }


    public void enqueue(int value) {
        add(value);
    }


    public int dequeu() {
        return remove(0);
    }
}