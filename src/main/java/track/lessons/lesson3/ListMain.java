package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();

        for (int i = 0; i < 1000; i++) {
            linkedList.add(i);
        }
        for (int i = 0; i < 1000; i++) {
            linkedList.push(i);
        }
        DynamicList dynamicList = new DynamicList();

        for (int i = 0; i < 1000; i++) {
            dynamicList.add(i);
        }
    }
}