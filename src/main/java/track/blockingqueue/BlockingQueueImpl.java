package track.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by geoolekom on 18.11.16.
 */
public class BlockingQueueImpl<T> implements BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<T>();
    private ReentrantLock full = new ReentrantLock();
    private ReentrantLock empty = new ReentrantLock();
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void put(T item) {
        full.lock();
        lock.lock();
        full.unlock();
        queue.add(item);
        empty.unlock();
        if (queue.size() == SIZE) {
            full.lock();
        }
        lock.unlock();
    }

    @Override
    public boolean offer(T item) {
        if (full.tryLock()) {
            lock.lock();
            full.unlock();
            queue.add(item);
            if (queue.size() == SIZE) {
                full.lock();
            }
            lock.unlock();
            return true;
        }
        return false;
    }

    @Override
    public T take() {
        empty.lock();
        lock.lock();
        empty.unlock();
        T ret;
        ret = queue.peek();
        full.unlock();
        if (queue.size() == 0) {
            empty.lock();
        }
        lock.unlock();
        return ret;
    }

    @Override
    public T poll(int timeout) {
        FutureTask<T> task = new FutureTask<>(this::take);
        task.run();
        T ret;
        try {
            ret = task.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            ret = null;
        }
        return ret;

    }
}
