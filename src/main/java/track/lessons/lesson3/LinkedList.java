package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List {

    Node head = null;
    Node tale = null;

    public LinkedList() {
        head = new Node(0);
        tale = head;
    }

    @Override
    public void add(int item) {
        tale = tale.append(item);
        size++;
    }

    @Override
    public int remove(int idx) {
        if (!checkIdx(idx)) {
            System.out.println("Out of range");
            return 0;
        }
        Node currNode = head.next();
        for (int i = 0; i < idx; i++) {
            currNode = currNode.next();
        }
        size--;

        if (currNode == tale) {
            tale = currNode.prev();
        }

        return currNode.remove();
    }

    @Override
    public int get(int idx) {
        if (!checkIdx(idx)) {
            System.out.println("Out of range");
            return 0;
        }
        Node currNode = head.next();
        for (int i = 0; i < idx; i++) {
            currNode = currNode.next();
        }
        return currNode.getValue();
    }

    @Override
    public void printList() {
        Node currNode = head.next();
        System.out.print("List = { ");
        while (currNode != null) {
            System.out.printf("%d ", currNode.getValue());
            currNode = currNode.next();
        }
        System.out.print("}\n");
    }
}
