package com.ambrosoft.exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created on 5/21/18
 */
public class BlockMining {
    static int N_THREADS = 8;

    static boolean GREENLIGHT = true;

    static class Worker implements Runnable {
        final int serial;

        Worker(int serial) {
            this.serial = serial;
        }

        @Override
        public void run() {
            while (GREENLIGHT) {
                long number = ThreadLocalRandom.current().nextLong();
                System.out.println("serial = " + serial + "\t" + number);
                if (number > 0L && number < Long.MAX_VALUE / 2000L) {
                    GREENLIGHT = false;
                    System.out.println(serial + " found " + number);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

        for (int i = 0; i < N_THREADS; i++) {
            executorService.submit(new Worker(i));
        }

        executorService.shutdown();

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("done");
    }

}
