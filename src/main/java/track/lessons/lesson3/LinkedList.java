package track.lessons.lesson3;

/**
 *
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

class LinkedList<T> extends List<T> implements Stack<T>, Queue<T> {

	Node<T> first;
	Node<T> last;

	LinkedList() {
		first = null;
		last = null;
		size = 0;
	}

	@Override
	void add(T item) {
		if (size == 0) {
            first = new Node<T>(null, item);
            last = first;
            size++;
		} else {
            Node<T> oldLast = last;
            last = new Node<T>(oldLast, item);
            oldLast.setNext(last);
            size++;
        }
	}

	@Override
	T remove(int index) {
		if(index >= size || index < 0) {
			System.out.println("Error!");
			return null;
		}

		Node<T> current = first;
		while(index-- > 0) {
			current = current.getNext();
		}

		if (current.getNext() != null) {
			current.getNext().setPrev(current.getPrev());
		} else {
            last = current.getPrev();
        }

		if (current.getPrev() != null) {
			current.getPrev().setNext(current.getNext());
		} else {
            first = current.getNext();
        }

		size --;
		return current.getValue();
	}

	@Override
	T get(int index) {
		if(index >= size || index < 0) {
			System.out.println("Error!");
			return null;
		}

		Node<T> current = first;
		while(index-- > 0) {
			current = current.getNext();
		}
		return current.getValue();
	}

	@Override
	public void enqueue(T value) {
		add(value);
	}

	@Override
	public T dequeue() {
		return remove(0);
	}

	@Override
	public void push(T value) {
		add(value);
	}
	@Override
	public T pop() {
		return remove(size - 1);
	}
}
