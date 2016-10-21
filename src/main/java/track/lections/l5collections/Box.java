package track.lections.l5collections;

/**
 * Пример типизации класса
 */
public class Box<T> {
    private T item;

    public Box() {
    }

    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        // Object - nothing more
    }

    @Override
    public String toString() {
        return "Box{" +
                "item=" + item +
                '}';
    }

    public static void main(String[] args) {

        Box<String> stringBox = new Box<String>();
        stringBox.setItem("A");
//        stringBox.setItem(2); // compile time err

        Box<Number> num = new Box<>();
        num.setItem(2.6);
    }
}
