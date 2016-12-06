package com.ambrosoft.exercises;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jacek on 6/19/16.
 */
public class CountDownTiming {
    static long time(Executor executor, Runnable action, int concurrency) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown();  // announce readiness
                try {
                    start.await();  // wait for first signal
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();   // announce finished
                }
            });
        }

        ready.await();
        final long begin = System.nanoTime();
        start.countDown();  // firing gun
        done.await();
        return System.nanoTime() - begin;
    }

    public static void main(String[] args) throws InterruptedException {
        final int concurrency = 4;
        final ExecutorService executor = Executors.newFixedThreadPool(concurrency);
        long val = time(executor, () -> System.out.println("hello"), concurrency);
        executor.shutdown();
        System.out.println("val = " + val);
    }

}
