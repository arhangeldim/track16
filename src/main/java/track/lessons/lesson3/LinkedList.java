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

    Node first;
    Node last;
    int size;

    void add(int item) {
        if (first == null) {
            first = new Node(null, item);
            last = first;
        } else {
            last.setNext(new Node(null, item));
            last = last.getNext();
        }
        size++;
    }

    int remove(int idx) {
        Node numberN = first;
        if (idx < 0 || idx >= size) { // * (idx >= size)
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        for (int i = 0; i < idx; i++) {
            numberN = numberN.getNext();
            if (numberN == null) { // Нам ведь получается не нужна эта проверка если есть *(52 строка).
                System.out.println("This list doesn't have element with this index.");
                return -1;
            }
        }
        if (numberN.prev != null) {
            numberN.prev.setNext(numberN.next);
        }
        if (numberN.next != null) {
            numberN.next.setPrev(numberN.prev);
        } else {
            last = numberN.prev;
        }
        if (idx == 0) {
            first = first.next;
        }
        size--;
        return numberN.value;
    }

    int get(int idx) {
        if (idx < 0 || idx >= size) {
            System.out.println("This list doesn't have element with this index.");
            return -1;
        }
        Node numberN = first;
        for (int i = 0; i < idx; i++) {
            numberN = numberN.getNext();
            if (numberN == null) { //Аналогично проверке в ремуве.
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
