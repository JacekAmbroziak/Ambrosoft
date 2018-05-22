package com.ambrosoft.exercises;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created on 5/21/18
 */
public class BlockMining {
    private final static int N_THREADS = 8;
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

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

    static class Solver implements Callable<Result> {
        private final int serial;
        private final byte[] data;

        Solver(final int serial, final byte[] data) {
            this.serial = serial;
            this.data = Arrays.copyOf(data, data.length + 8);
        }

        @Override
        public Result call() throws Exception {
            final MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            int counter = 0;
            final byte[] data = this.data;
            final int datalen = data.length;
            final Thread currentThread = Thread.currentThread();
            while (!currentThread.isInterrupted()) {
                final long longNonce = random.nextLong();

                data[datalen - 1] = (byte) (longNonce & 0xFF);
                data[datalen - 2] = (byte) ((longNonce >>> 8) & 0xFF);
                data[datalen - 3] = (byte) ((longNonce >>> 16) & 0xFF);
                data[datalen - 4] = (byte) ((longNonce >>> 24) & 0xFF);
                data[datalen - 5] = (byte) ((longNonce >>> 32) & 0xFF);
                data[datalen - 6] = (byte) ((longNonce >>> 40) & 0xFF);
                data[datalen - 7] = (byte) ((longNonce >>> 48) & 0xFF);
                data[datalen - 8] = (byte) ((longNonce >>> 56) & 0xFF);

                final byte[] result = sha256.digest(data);
                ++counter;
                if (result[0] == 0 && result[1] == 0 && result[2] == 0 && (result[3] & 0xFF) <= 0x0F) {
                    System.out.println(serial + " found " + toHex(result, 32));
                    System.out.println("serial = " + serial + "\t" + longNonce);
                    return new Result(longNonce);
                }
            }
            System.out.println("interrupted serial = " + serial + "\tcounter = " + counter);
            return null;
        }
    }

    static void solve(final Executor executor, final List<Callable<Result>> solvers) throws InterruptedException {
        final CompletionService<Result> ecs = new ExecutorCompletionService<>(executor);
        final int n = solvers.size();
        final Future[] futures = new Future[n];
        Result result = null;
        try {
            for (int i = n; --i >= 0; ) {
                futures[i] = ecs.submit(solvers.get(i));
            }
            for (int i = n; --i >= 0; ) {
                try {
                    final Result r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch (ExecutionException ignore) {
                }
            }
        } finally {
            System.out.println("cancelling others");
            for (Future f : futures) {
                f.cancel(true);
            }
        }

        if (result != null) {
            use(result);
        }
    }


    private static String toHex(final byte[] bytes, final int count) {
        final char[] chars = new char[count << 1];
        for (int i = 0, j = 0; i < count; ++i, j += 2) {
            final int v = bytes[i] & 0xff;
            chars[j] = HEX_DIGITS[v >> 4];
            chars[j + 1] = HEX_DIGITS[v & 0xF];
        }
        return String.valueOf(chars);
    }

    static void use(Result result) {
        System.out.println("result = " + result);
    }


    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

        final byte[] data = new byte[120];
        new Random().nextBytes(data);

        final ArrayList<Callable<Result>> solvers = new ArrayList<>(N_THREADS);
        for (int i = N_THREADS; --i >= 0; ) {
            solvers.add(new Solver(i, data));
        }

        solve(executorService, solvers);

        executorService.shutdownNow();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }
}