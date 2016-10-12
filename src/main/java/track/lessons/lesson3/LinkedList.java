package track.lessons.lesson3;

public class LinkedList extends List implements Queue, Stack {

    class Node {
        private Node next;
        private Node prev;
        private int value;

        Node() {
            next = null;
            prev = null;
            value = 0;
        }

        public void SetNext(Node node) {
            this.next = node;
        }

        public void SetPrev(Node node) {
            this.prev = node;
        }

        public void SetValue(int value) {
            this.value = value;
        }

        public Node GetNext() {
            return this.next;
        }

        public Node GetPrev() {
            return this.prev;
        }

        public int GetValue() {
            return this.value;
        }
    }

    private int size;
    private Node root;

    LinkedList() {
        root = new Node();
        size = 0;
        root.SetValue(0);
    }

    @Override
    public void add(int item) {
        Node newnode = new Node();
        newnode.SetValue(item);
        if (size == 0) {
            root = newnode;
        } else {
            Node search = root;
            while (search.GetNext() != null) {
                search = search.GetNext();
            }
            search.SetNext(newnode);
            newnode.SetPrev(search);
        }
        size++;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx > size - 1) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            if (idx == 0) {
                root = root.GetNext();
                size--;
                return root.GetValue();
            }
            Node delete = root;
            int item = 0;
            while (i != idx) {
                delete = delete.GetNext();
                item = item + 1;
            }
            if (idx != size - 1) {
                delete.GetPrev().SetNext(delete.GetNext());
                delete.GetNext().SetPrev(delete.GetPrev());
            } else {
                delete.GetPrev().SetNext(null);
            }
            size--;
            return delete.GetValue();
        }
    }

    @Override
    public int get(int idx) {
        if (idx < 0 || idx > size - 1) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            Node search = new Node();
            search = root;
            int item = 0;
            while (item != idx) {
                search = search.GetNext();
                item++;
            }
            return search.GetValue();
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