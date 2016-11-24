package track.lessons.lesson3;


public class LinkedList extends List {
    private class Node {
        private Node next;
        private Node prev;
        private int value;
    }

    private Node head;
    private Node tail;
    int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    void add(int item) {
        Node node = new Node();
        if (isEmpty()) {
            node.value = item;
            node.next = node;
            node.prev = node;
            tail = node;
            head = node;
            size++;
        } else {
            node.value = item;
            node.prev = tail;
            node.next = head;
            tail = node;
            head.prev = tail;
            size++;
        }
    }

    int size() {
        return size;
    }

    int get(int idx) {
        if (idx < 0 || idx >= size) {
            System.out.println("ERROR");
            return -1;
        }

        if (idx == 0) {
            return head.value;
        }
        if (idx == size - 1) {
            return tail.value;
        }

        Node node = head;
        for (int i = 0; i <= idx; i++) {
            node = node.next;
        }
        return node.value;

    }

    int remove(int idx) {
        if (idx < 0 || idx >= size) {
            System.out.println("ERROR");
            return -1;
        }
        size--;
        if (idx == 0) {
            int value = head.value;
            if (head.next != null) {

                head = head.next;
                head.prev = tail;
                tail.next = head;

            } else {
                head = null;
            }
            return value;
        }
        if (idx == size) {
            int value = tail.value;

            if (tail.prev != null) {
                tail = tail.prev;
                tail.next = head;
                head.prev = tail;
            } else {
                tail = null;
            }
            return value;
        } else {
            Node node = head;
            for (int i = 0; i <= idx; i++) {
                node = node.next;
            }
            int value = node.value;
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return value;

        }


    }


}

