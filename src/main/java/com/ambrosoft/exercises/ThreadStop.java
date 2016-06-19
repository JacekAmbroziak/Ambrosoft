package com.ambrosoft.exercises;

import java.util.concurrent.TimeUnit;

/**
 * Created by jacek on 6/18/16.
 */
public class ThreadStop {
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                ++i;
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);

        stopRequested = true;

        System.out.println("done");
    }
}
