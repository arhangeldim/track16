package track.lections.l7threads;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *  Несколько способоа остановить поток
 */
public class StopThread {

    // Остановка с выставлением флага
    static class FlagThread extends Thread {
        private volatile boolean pleaseStop;

        @Override
        public void run() {
            int iter = 0;
            while (!pleaseStop) {
                try {
                    System.out.println("Thread::" + iter++);
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    //
                }
            }
        }

        public void stopThread() {
            System.out.println("Stopping...");
            pleaseStop = true;
        }
    }

    // С помощью стандартного механизма прерывания
    static class InterThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    System.out.println("Thread::sleep()");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    // Нас прервали
                    System.out.println("thread interrupted");
                    return;
                }
            }
        }
    }

    // Без какой-либо обработки прерывания
    static class DummyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("q");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }

    public static void flagThread() {
        FlagThread flagThread = new FlagThread();
        flagThread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        flagThread.stopThread();
    }

    public static void interruptThread() {
        Thread thread = new InterThread();
        thread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        thread.interrupt();
    }

    public static void dummyThread() {
        Thread thread = new DummyThread();
        thread.start();

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        thread.interrupt();
    }

    public static void main(String[] args) throws Exception {
        //flagThread();

        //interruptThread();
        dummyThread();
    }

}
