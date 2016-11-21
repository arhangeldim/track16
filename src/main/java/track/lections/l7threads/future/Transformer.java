package track.lections.l7threads.future;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import track.lections.Util;


/**
 *
 */
public class Transformer<T extends Image> {

    public Image transform(Image image) {

        // sleep for 2 seconds - imitate long transformation
        Util.sleepQuietly(TimeUnit.SECONDS, 2);
        return image;
    }
}
