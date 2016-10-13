package track.lessons.lesson3;

/**
<<<<<<< HEAD
 * Created by frystile on 13.10.16.
 */
public class ListMain {
    public static void main(String[] args) {


        List list = new DynamicList();
        list.add(1);
        list.add(2);
        list.add(10);
        int first = list.remove(0);
        int second = list.get(1);

        System.out.println("size = " + list.size());
        System.out.println("удаленный элемент = " + first);
        System.out.println("второй элемент = " + second);

        list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(10);
        first = list.remove(0);
        second = list.get(1);

        System.out.println("\nsize = " + list.size());
        System.out.println("удаленный элемент = " + first);
        System.out.println("второй элемент = " + second);

        LinkedList queue = new LinkedList();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(10);
        first = queue.dequeu();
        System.out.println("\nпервый в очереди = " + first);

        queue = new LinkedList();
        queue.push(1);
        queue.push(2);
        queue.push(10);
        first = queue.pop();
        second = queue.pop();
        System.out.println("\nпервый в стеке = " + first);
        System.out.println("второй в стеке = " + second);

=======
 *
 */
public class ListMain {

    public static void main(String[] args) {


//        List list = new DynamicList();
//        list.add(1);
//        list.add(2);
//        list.add(10);
//        int first = list.remove(0);
>>>>>>> arch/master

    }
}
