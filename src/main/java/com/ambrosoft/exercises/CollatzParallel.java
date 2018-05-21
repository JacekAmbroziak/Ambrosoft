package com.ambrosoft.exercises;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created on 2/13/18
 */
public class CollatzParallel {
    private final static int NTHREADS = 8;
    private final static BigInteger THREE = new BigInteger("3");

    private static Boolean check(final long n, Set<BigInteger> visited) {
        if (n == 1L) {
            return true;
        } else {
            BigInteger num = new BigInteger(Long.toString(n));
            while (true) {
                if (num.testBit(0)) {   // odd
                    num = THREE.multiply(num).add(BigInteger.ONE);
                } else {
                    num = num.shiftRight(1);
                }
                if (num.equals(BigInteger.ONE)) {
                    return true;
                } else if (!visited.add(num)) {
                    return false;
                }
            }
        }
    }

    static Boolean check(final long n) {
        return check(n, new HashSet<>());
    }

    static class IntervalCheck implements Callable<Boolean> {
        private final long low;
        private final long high;

        IntervalCheck(long low, long high) {
            this.low = low;
            this.high = high;
        }

        @Override
        public Boolean call() throws Exception {
            System.out.println(String.format("lo = %d, hi = %d", low, high));
            for (long n = low; n <= high; n++) {
                if (!check(n)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    static void checkUpTo(final long upper) {
        final ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS);
        final ArrayList<Future<Boolean>> results = new ArrayList<>();

        final long rangeLen = (upper / NTHREADS + 1) / 2;
//        final long rangeLen = 1000L;
        for (long lo = 1L, hi = rangeLen; lo < upper; lo += rangeLen, hi += rangeLen) {
            final IntervalCheck checker = new IntervalCheck(lo, Math.min(hi, upper));
            results.add(executorService.submit(checker));
        }
        try {
            for (Future<Boolean> result : results) {
                if (result.get() == Boolean.FALSE) {
                    System.out.println("false");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("exception");
        }
        System.out.println("done");
        executorService.shutdown();
    }

    public static void main(String[] args) {
        checkUpTo(10000000L);
    }
}
