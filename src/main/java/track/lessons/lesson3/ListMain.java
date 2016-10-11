package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {

        LinkedList list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(10);
        list.push(123);
        list.enqueue(312);
        list.dequeu();
        list.pop();
        list.dump();
        //int first = list.remove(1);


    }
}
