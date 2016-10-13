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

        public void setNext(int value) {
            next = new Node(this, value);
        }

        public int getValue() {
            return value;
        }

        public Node getLast() {
            if (this.next == null) {
                return this;
            } else {
                return this.next.getLast();
            }
        }

        public Node getN(int number) {
            if (number == 0) {
                return this;
            } else if (this.next == null) {
                return null;
            } else {
                return this.next.getN(number - 1);
            }
        }

        public int size() {
            if (next == null) {
                return 1;
            } else {
                return 1 + next.size();
            }
        }
    }

    Node content;

    public LinkedList() {
        content = null;
    }

    void add(int item) {
        if (content == null) {
            content = new Node(null, item);
        } else {
            content.getLast().setNext(item);
        }
    }

    int remove(int idx) {
        Node numberN = content.getN(idx);
        if (numberN == null) {
            System.out.println("This list doesn't have element with this index.");
            return 0;
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
        return numberN.value;
    }

    int get(int idx) {
        Node numberN = content.getN(idx);
        if (numberN == null) {
            System.out.println("This list doesn't have element with this index.");
            return 0;
        }
        return numberN.getValue();
    }

    int size() {
        return content.size();
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
