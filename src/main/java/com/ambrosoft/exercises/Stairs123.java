package com.ambrosoft.exercises;

/**
 * Created by jacek on 7/5/16.
 */
public class Stairs123 {


    static long countWays(final int n) {
//        System.out.println("call n = " + n);
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;   // one step
        } else if (n == 2) {
            return 2;   // one-one, two
        } else if (n == 3) {
            return 4;   // 3;1,2;2,1;1,1,1
        } else {
            return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
    }

    static long countWaysMemo(final int n) {
        final long[] memo = new long[n + 3];
        memo[1] = 1;
        memo[2] = 2;
        memo[3] = 4;
        return countWays(n, memo);
    }

    static long countWays(int n, long[] memo) {
        if (memo[n] != 0) {
            return memo[n];
        } else {
            return memo[n] = countWays(n - 1, memo) + countWays(n - 2, memo) + countWays(n - 3, memo);
        }
    }


    static void printResults(int n) {
        System.out.println("n = " + n + "\t" + countWays(n));
        System.out.println("n = " + n + "\t" + countWaysMemo(n));
    }

    public static void main(String[] args) {
        printResults(10);
        printResults(20);
        printResults(30);
        printResults(40);
    }

}
