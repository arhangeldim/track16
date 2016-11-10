package track.lections.l7threads.counting;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class TwoLockCounter implements Counter {

    Lock l1 = new ReentrantLock();
    Lock l2 = new ReentrantLock();

    private long counter;

    @Override
    public long inc() {
        long val;

        l1.lock();
        val = counter++;
        l1.unlock();
        return val;
    }

    public long inc2() {
        long val;

        l2.lock();
        val = counter++;
        l2.unlock();
        return val;
    }
}
