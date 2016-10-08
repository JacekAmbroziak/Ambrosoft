package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/6/16.
 */

public class DivisibleTriangular {
    static int countDivisors(final long n) {
        int count = 1;
        long limit = (long) Math.sqrt(n);
        for (long i = 2; i <= limit; ++i) {
            if (n % i == 0) {
                ++count;
            }
        }
        return count * 2;
    }

    static void search() {
        long triangular = 1;
        long addition = 1;
        while (true) {
            triangular += ++addition;
            if (countDivisors(triangular) > 500) {
                System.out.println("triangular = " + triangular);
                return;
            }
        }
    }

    public static void main(String[] args) {
        search();
    }
}
