package com.ambrosoft.exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jacek on 6/16/16.
 */
public class ThreeThreads {
    public static void main(String[] args) {

        final int nThreads = 3;

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        Worker[] workers = new Worker[nThreads];

        workers[0] = new Worker("zero", workers, 0);
        workers[1] = new Worker("one", workers, 1);
        workers[2] = new Worker("two", workers, 2);

        for (Worker worker : workers) {
            executor.execute(worker);
        }

        executor.shutdown();
    }

    static class Worker implements Runnable {
        final String message;
        final Worker[] squad;
        final int number;

        Worker(String message, Worker[] squad, int no) {
            this.message = message;
            this.squad = squad;
            number = no;
        }

        @Override
        public void run() {
            final int prevIdx = (number == 0 ? squad.length : number) - 1;
            int i = 20;
            while (--i >= 0) {
                try {
                    System.out.println(message);

                    synchronized (this) {
                        notify();
                    }

                    final Worker prev = squad[prevIdx];
                    synchronized (prev) {
                        prev.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("done");
        }
    }
}
