package track.lections.l7threads.counting;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
public class MyLockCounter implements Counter {

    long counter = 0;

    MyLock lock = new MyLock();

    @Override
    public long inc() {
        long val;
        lock.lock();
        val = counter++;
        lock.unlock();
        return val;
    }
}

// Кастомная реализация блокировки
class MyLock {
    AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (!locked.compareAndSet(false, true)) {};
    }

    public void unlock() {
        locked.set(false);
    }

}
