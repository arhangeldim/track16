package track.lections;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Util {

    public static void sleepQuietly(TimeUnit unit, int count) {
        try {
            unit.sleep(count);
        } catch (InterruptedException ignored) { // ignored
        }
    }
}
