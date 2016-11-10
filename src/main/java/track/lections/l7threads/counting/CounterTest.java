package track.lections.l7threads.counting;

/**
 *
 */
public class CounterTest {

    public static void main(String[] args) throws Exception {
        testCounter();
        //testSafeUnsafe();
        //testSafeUnsafe2();
    }

    public static void testCounter() throws Exception {
        final int threadNum = 4;
        Counter counter = new SimpleCounter();

        //Counter counter = new AtomicCounter();
        //LockCounter counter = new LockCounter();

        //Counter counter = new MyLockCounter();

        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) {
                    counter.inc();
                }
            });

            threads[i] = thread;
            thread.start();
        }

        // Дожидаемся всех
        for (Thread t : threads) {
            t.join();
        }

        System.out.printf("Threads: %d\nCounter: %d", threadNum, counter.inc());
    }

    public static void testSafeUnsafe() throws Exception {

        final int threadNum = 2;
        LockCounter counter = new LockCounter();
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    counter.inc();
                }
            });

            threads[i] = thread;
            thread.start();
        }
        Thread unsafe = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                // инкремент без взятия блокировки
                counter.incUnsafe();
            }
        });
        unsafe.start();

        for (Thread t : threads) {
            t.join();
        }

        unsafe.join();

        System.out.printf("Threads: %d\nCounter: %d", threadNum, counter.inc());
    }

    public static void testSafeUnsafe2() throws Exception {

        final int threadNum = 2;
        TwoLockCounter counter = new TwoLockCounter();
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100_000; j++) {
                    counter.inc();
                }
            });

            threads[i] = thread;
            thread.start();
        }
        Thread unsafe = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                // инкремент без взятия блокировки
                counter.inc2();
            }
        });
        unsafe.start();

        for (Thread t : threads) {
            t.join();
        }

        unsafe.join();

        System.out.printf("Threads: %d\nCounter: %d", threadNum, counter.inc());
    }


}
