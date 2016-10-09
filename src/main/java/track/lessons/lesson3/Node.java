package track.lessons.lesson3;

/**
 * Created by geoolekom on 09.10.16.
 */
class Node<T> {

    private Node<T> next;
    private Node<T> prev;
    private T value;

    Node(Node<T> prev, T value) {
        this.value = value;
        this.prev = prev;
        this.next = null;
    }

    T getValue() {
        return value;
    }

    Node<T> getNext() {
        return next;
    }

    Node<T> getPrev() {
        return prev;
    }

    void setValue(T value) {
        this.value = value;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }

    void setPrev(Node<T> prev) {
        this.prev = prev;
    }

}

