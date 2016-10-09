package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
class Node<T> {

    private Node<T> next;
    private Node<T> prev;
    private T value;

    public Node(Node<T> prev, T value) {
        this.value = value;
        this.prev = prev;
        this.next = null;
    }

    T getValue() {
        return value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

}

