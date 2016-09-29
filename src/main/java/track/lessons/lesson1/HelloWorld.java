package track.lessons.lesson1;

/**
 *
 */
public class HelloWorld {

    public static void main(String[] args) {
        float a = Float.MIN_NORMAL;
        float b = Float.MIN_NORMAL;
        float c = Float.MAX_VALUE;
        System.out.println(Boolean.toString((a*b)*c == a*(b*c)));

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg[" + i + "]=" + args[i]);
        }
    }
}
