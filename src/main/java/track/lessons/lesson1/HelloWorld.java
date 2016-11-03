package track.lessons.lesson1;

import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

/**
 *
 */
public class HelloWorld {

    public static void main(String[] args) {
        String str = null;
        if (str != null && str.equals("A")) {
            int x = 1;
        }
        if (str != null & str.equals("A")) {
            int x = 1;
        }
        if (str != null && str == "A") {
            int x = 1;
        }
    }
}
