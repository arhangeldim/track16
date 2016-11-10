package track.lections.l7threads.counting;

/**
 *
 */
public class SimpleCounter implements Counter {
    private long val;

    public long inc() {
        return val++;
    }

}
