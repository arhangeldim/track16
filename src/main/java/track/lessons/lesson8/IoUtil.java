package track.lessons.lesson8;
import java.io.Closeable;

public class IoUtil {

    static void closeQuietly(Closeable res) {
        if (res != null) {
            try {
                res.close();
            } catch (Exception e) {
                // tsss!
            }
        }
    }

    static void closeQuietly(Closeable... list) {
        for (Closeable res : list) {
            closeQuietly(res);
        }
    }
}
