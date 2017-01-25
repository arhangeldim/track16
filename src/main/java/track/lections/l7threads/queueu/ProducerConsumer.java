package track.lections.l7threads.queueu;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerConsumer {

    static Logger log = LoggerFactory.getLogger(ProducerConsumer.class);

    static List<String> list = new ArrayList<>();

    static class Producer extends Thread {
        private final Object lock;

        public Producer(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            log.info("[PRODUCER] Preparing data...");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("[PRODUCER] Data prepared.");


            synchronized (lock) {
                list.add("READY");
                lock.notifyAll();
            }
        }
    }

    static class Consumer extends Thread {
        private final Object lock;

        public Consumer(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            // 1) Захватываем блокировку
            synchronized (lock) {
                try {
                    log.info("[CONSUMER] Waiting for data...");

                    // 1) Если данные еще не готовы  - ждем. Но wait() отпускает блокировку, при этом текущий поток
                    // переходит в состояние WAITING и помещается в wait set монитора
                    while (list.isEmpty()) {
                        lock.wait();
                        // как только пробудились, заново проверяем состояние данных
                        // если они не готовы (или кто-то уже их поменял), то снова ждем
                    }

                    // 2) Данные готовы, мы внутри КС - можно изменять данные
                    System.out.println("[CONSUMER] received: " + list.get(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static void main(String[] args) {
        Object lock = new Object();
        new Consumer(lock).start();
        new Producer(lock).start();
    }
}
