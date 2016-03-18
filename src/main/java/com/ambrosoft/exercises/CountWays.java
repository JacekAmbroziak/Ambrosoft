package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 3/17/16.
 */
public class CountWays {

    private static long countDP(final int n, long[] cache) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else if (cache[n] >= 0) {
            return cache[n];
        } else {
            return cache[n] = countDP(n - 1, cache) + countDP(n - 2, cache) + countDP(n - 3, cache);
        }
    }

    private static long countDPslow(final int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        } else {
            return countDPslow(n - 1) + countDPslow(n - 2) + countDPslow(n - 3);
        }
    }

    public static void main(String[] args) {
        long[] cache = new long[128];
        Arrays.fill(cache, -1);

        int N = 30;
        System.out.println("count = " + countDP(N, cache));
        System.out.println("count = " + countDPslow(N));
    }
}
