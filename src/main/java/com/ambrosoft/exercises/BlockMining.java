package com.ambrosoft.exercises;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
            try {
                final MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                while (GREENLIGHT) {
                    final long number = ThreadLocalRandom.current().nextLong();
                    final byte[] result = sha256.digest(Long.toHexString(number).getBytes());
//                    System.out.println(Arrays.toString(result));
                    if (result[0] == 0 && result[1] == 0 && result[2] == 0) {
                        GREENLIGHT = false;
                        System.out.println(serial + " found " + Arrays.toString(result));
                        System.out.println("serial = " + serial + "\t" + number);
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
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
