package track.lessons.lesson3;

import track.lessons.lesson3.List;
/**
 *
 */

class Node {
    Node(int val) {
        value = val;
    }

    protected Node next;
    protected Node prev;
    protected int value;
}

public class LinkedList extends List {
    Node init = new Node(0);
    private int size = 0;

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("List:size=" + size + "[");
        ret.append("basenode->");
        Node cur = (Node)init.next;
        while (cur != null) {
            ret.append(cur.value + "->");
            cur = cur.next;
        }
        ret.append("null]");
        return ret.toString();
    }

    @Override
    public int pop() throws Exception {
        if (size < 1) {
            throw new Exception("Stack is empty");
        }
        return remove(size - 1);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public void enqueue(int value) {
        Node inserted = insert(0, value);
        if (inserted == null) {
            throw new RuntimeException("Can't enqueue to LinkedList");
        }
    }

    @Override
    public int dequeu() throws Exception {
        return pop();
    }

    public void add(int item) {
        Node last = findLast(init);
//        System.out.println("Adding: last " + last);
        Node adding = new Node(item);
        adding.prev = last;
        last.next = adding;
//        System.out.println("Adding: new " + last.next);
        size++;
    }

    public int remove(int idx) throws Exception {
        Node node = findIdx(idx);
        if (node == null) {
            throw new IllegalArgumentException("No element with such index:" + idx);
        }
        int returnValue = get(idx);
        node.prev.next = node.next;
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
        return node.value;

    }

    public int get(int idx) throws Exception {
        Node node = findIdx(idx);
        if (node == null) {
            throw new IllegalArgumentException("No element with such index:" + idx);
        }
        return node.value;
    }

    public int size() {
        return size;
    }


    private Node findLast(Node begin) {
        while (begin.next != null) {
            begin = begin.next;
        }
        return begin;
    }

    private Node findIdx(int idx) {
        if (!(0 <= idx && idx < size)) {
            return null;
        }
        Node node = init.next;
        while (idx > 0) {
            node = node.next;
            idx -= 1;
        }
        return (Node)node;
    }

    private Node insert(int idx, int val) {
        if (!(0 <= idx && idx <= size)) {
            return null;
        }
        if (idx == size) {
            add(val);
            return findIdx(idx);
        }
        Node inserting = new Node(val);
        Node insertingTo = findIdx(idx);
//        System.out.println(inserting + " " + insertingTo);
        if (insertingTo == null) {
            return null;
        }
//        System.out.println(toString());
        inserting.next = insertingTo;
        inserting.prev = insertingTo.prev;
        insertingTo.prev.next = inserting;
        insertingTo.prev = inserting;
        size += 1;
        return inserting;
    }

    public boolean check() {
        if (size < 0) {
            return false;
        }
        int count = 0;
        Node cur = init.next;
        while (cur != null) {
            count += 1;
            cur = cur.next;
        }
        if (count != size) {
            System.out.println("1");
            return false;
        }
        if (size > 0) {
            Node last = findIdx(size - 1);
            System.out.println("Found last " + last);
            int backCounting = size;
            System.out.println("Size " + size);
            while (backCounting > 0) {
                if (last == null) {
                    System.out.println("2");
                    return false;
                }
                last = last.prev;
                backCounting -= 1;
            }
            if (last != init) {
                System.out.println(3);
                return false;
            }
        }
        return true;
    }


}
