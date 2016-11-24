package track.lessons.lesson1;

/**
 *
 */
public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello");
        for (int i = 0; i < args.length; i++) {
            System.out.println("arg[ " + i + " ]=" + args[i]);
        }
    }
}
