package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: jacek
 * Date: 5/29/18
 * Time: 11:03 AM
 * <p/>
 * Copyright 2018 Ambrosoft, Inc. All Rights Reserved.
 * This software is the proprietary information of Ambrosoft, Inc.
 * Use is subject to license terms.
 *
 * @author Jacek R. Ambroziak
 */
final class RepeatRandom {
    private final static int N_THREADS = 8;

    static class Result {
        private final long nonce;

        Result(final long nonce) {
            this.nonce = nonce;
        }

        @Override
        public String toString() {
            return Long.toString(nonce);
        }
    }

    static class Worker implements Callable<Result> {
        private final int serial;
        private final AtomicLong[] accumulators;

        Worker(final int serial, final AtomicLong[] accumulators) {
            this.serial = serial;
            this.accumulators = accumulators;
        }

        @Override
        public Result call() throws Exception {
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            long accumulator = 0L;
            int counter = 0;
            final Thread currentThread = Thread.currentThread();
            final int length = accumulators.length;
            while (!currentThread.isInterrupted()) {
                final long number = random.nextLong();

                System.out.println("number = " + number);

                accumulator |= number;

                ++counter;
                if (counter % 1000 == 0) {

                    final long local = accumulator;
                    for (int i = length; --i >= 0; ) {
                        if (i == serial) {
                            accumulators[i].updateAndGet(val -> val | local);
                        } else {
                            if ((local & accumulators[i].get()) != 0) {
                                System.out.println(serial + " i = " + i);
                                return new Result(local & accumulators[i].get());
                            }
                        }
                    }
                }
            }
            System.out.println("interrupted thread " + serial + ", counter = " + counter);
            return null;
        }
    }

    static void use(Result result) {
        System.out.println("result = " + result);
    }

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        final AtomicLong[] accumulators = new AtomicLong[N_THREADS];
        final ArrayList<Callable<Result>> solvers = new ArrayList<>(N_THREADS);
        for (int i = N_THREADS; --i >= 0; ) {
            accumulators[i] = new AtomicLong();
            solvers.add(new Worker(i, accumulators));
        }

//        solve(executorService, solvers);

        final long start = System.currentTimeMillis();
        long end = start;
        try {
            // a good example of the rule to "know your library"
            // invokeAny does exactly what is needed here (and uses CompletionService internally)
            final Result result = executorService.invokeAny(solvers);
            end = System.currentTimeMillis();
            use(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        System.out.println("number of hashes = " + accumulators);
        System.out.println("time = " + (end - start) / 1000L + " seconds");
    }
}
