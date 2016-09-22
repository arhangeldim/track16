package track.lessons.lesson1;

/**
 *
 */
public class HelloWorld {

    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println(args[0]);
        }
        System.out.println("Hello");
        try {
            System.out.println("");
        } catch (RuntimeException e) {
            System.out.println("");
        }
    }
}
