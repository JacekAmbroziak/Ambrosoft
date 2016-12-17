package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/10/16.
 */
public class RodCutting {
    // zero added so the array behaves as 1-based
    static final int[] PRICES = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    static int recursive(final int n) {
        int value = PRICES[n];  // full length, uncut for comparison
        for (int i = 1; i < n; i++) {
            value = Math.max(value, recursive(i) + recursive(n - i));
        }
        return value;
    }

    static int recursive2(final int n) {
        if (n == 0) {
            return 0;
        } else {
            int value = PRICES[n];
            for (int i = 1; i < n; i++) {
                value = Math.max(value, PRICES[i] + recursive2(n - i));
            }
            return value;
        }
    }

    static int memoized(final int n) {
        final int[] memo = new int[n + 1];
        Arrays.fill(memo, Integer.MIN_VALUE);
        memo[0] = 0;
        memoized(n, memo);
        return memo[n];
    }

    private static int memoized(final int n, final int[] memo) {
        if (memo[n] >= 0) {
            return memo[n]; // use computed previously
        } else {
            int value = PRICES[n];
            for (int i = 1; i < n; i++) {
                value = Math.max(value, PRICES[i] + memoized(n - i, memo));
            }
            return memo[n] = value; // store
        }
    }

    static int bottomUp(final int n) {
        final int[] results = new int[n + 1];
        // populate results table starting from smallest problems
        for (int i = 1; i <= n; i++) {  // i: problem for rod length i
            int value = Integer.MIN_VALUE;  // unknown
            for (int j = 1; j <= i; ++j) {
                value = Math.max(value, PRICES[j] + results[i - j]);   // smaller problems already computed
            }
            results[i] = value;
        }
        return results[n];
    }

    static int withSolution(final int n) {
        final int[] results = new int[n + 1];
        final int[] choices = new int[n + 1];

        for (int i = 1; i <= n; i++) {  // i: problem for rod length i
            int value = Integer.MIN_VALUE;  // unknown
            for (int j = 1; j <= i; ++j) {
                final int sample = PRICES[j] + results[i - j];
                if (sample > value) {
                    value = sample;
                    choices[i] = j; // currently best choice for 1st cut for problem of size i
                }
            }
            results[i] = value;
        }

        int len = n;
        while (len > 0) {
            final int choice = choices[len];
            System.out.println("\tchoices = " + choice + "\t" + PRICES[choice]);
            len -= choice;
        }

        return results[n];
    }

    public static void main(String[] args) {
        for (int arg = 1; arg <= 10; arg++) {
            System.out.println("----- " + arg);
            System.out.println("rec1 = " + recursive(arg));
            System.out.println("rec2 = " + recursive2(arg));
            System.out.println("mem1 = " + memoized(arg));
            System.out.println("btup = " + bottomUp(arg));
            System.out.println("wsol = " + withSolution(arg));
        }
    }
}
