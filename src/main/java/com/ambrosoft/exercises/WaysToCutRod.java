package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/11/16.
 */
public class WaysToCutRod {

    // my attempt at 'partition'
    static void cut(int n) {
        if (n == 0) {

        } else if (n == 1) {
            System.out.println("keep 1");
        } else {
            for (int i = 1; i < n; i++) {
                System.out.println("cut i = " + i);
                cut(n - i);
            }
        }
    }

    static void partition(int n) {
        partition(n, n, "");
    }

    // n -- remaining quantity to partition
    // prefix -- accumulated addends
    private static void partition(final int n, final int max, final String prefix) {
        if (n == 0) {
            System.out.println(prefix);
        } else {
            // try addends not bigger than n and limited by max, down to 1
            for (int i = Math.min(max, n); i >= 1; i--) {
                // an addend i is accumulated into prefix
                // and also becomes a limit for the recursive call
                // so no bigger addends will be considered and their sequence will be non-ascending
                partition(n - i, i, prefix + ' ' + i);
            }
        }
    }

    public static void main(String[] args) {
//        cut(10);
        partition(3);
    }
}
