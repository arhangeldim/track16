package track.lessons.lesson3;

/**
 *
 */
public class LinkedList extends List {

    Node head = null;
    Node tale = null;

    LinkedList() {
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
        Node iterator = head.next();
        for (int i = 0; i < idx; i++) {
            iterator = iterator.next();
        }
        size--;

        if (iterator == tale) {
            tale = iterator.prev();
        }

        return iterator.remove();
    }

    @Override
    public int get(int idx) {
        if (!checkIdx(idx)) {
            System.out.println("Out of range");
            return 0;
        }
        Node iterator = head.next();
        for (int i = 0; i < idx; i++) {
            iterator = iterator.next();
        }
        return iterator.get();
    }

    @Override
    public void printList() {
        Node iterator = head.next();
        System.out.print("List = { ");
        while (iterator != null) {
            System.out.printf("%d ", iterator.get());
            iterator = iterator.next();
        }
        System.out.print("}\n");
    }
}
