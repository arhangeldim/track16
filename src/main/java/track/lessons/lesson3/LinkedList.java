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

        public void Set_next(Node node) {
            this.next = node;
        }

        public void Set_prev(Node node) {
            this.prev = node;
        }

        public void Set_value(int value) {
            this.value = value;
        }

        public Node Get_next() {
            return this.next;
        }

        public Node Get_prev() {
            return this.prev;
        }

        public int Get_value() {
            return this.value;
        }
    }

    private int size;
    private Node root;

    LinkedList() {
        root = new Node();
        size = 0;
        root.Set_value(0);
    }

    @Override
    public void add(int item) {
        Node new_node = new Node();
        new_node.Set_value(item);
        if (size == 0) {
            root = new_node;
        } else {
            Node search = root;
            while (search.Get_next() != null) {
                search = search.Get_next();
            }
            search.Set_next(new_node);
            new_node.Set_prev(search);
        }
        size++;
    }

    @Override
    public int remove(int idx) {
        if (idx < 0 || idx > size - 1) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            if (idx == 0) {
                root = root.Get_next();
                size--;
                return root.Get_value();
            }
            Node delete = root;
            int i = 0;
            while (i != idx) {
                delete = delete.Get_next();
                i = i + 1;
            }
            if (idx != size - 1) {
                delete.Get_prev().Set_next(delete.Get_next());
                delete.Get_next().Set_prev(delete.Get_prev());
            } else {
                delete.Get_prev().Set_next(null);
            }
            size--;
            return delete.Get_value();
        }
    }

    @Override
    public int get(int idx) {
        if (idx < 0 || idx > size - 1) {
            throw new IndexOutOfBoundsException("Index is incorrect");
        } else {
            Node search = new Node();
            search = root;
            int i = 0;
            while (i != idx) {
                search = search.Get_next();
                i++;
            }
            return search.Get_value();
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