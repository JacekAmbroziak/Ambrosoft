package com.ambrosoft.exercises;

import java.util.Arrays;

public class FibonacciProduct {

    /*
        search for a pair of consecutive Fibonacci numbers that can multiply to given prod
     */

    static long[] productFib(final long prod) {
        for (long fa = 0L, fb = 1L; ; ) {
            final long divided = prod / fb;
            final long next = fa + fb;
            if (divided == next) {
                return new long[]{fb, next, 1L};
            } else if (divided < next) {
                return new long[]{fb, next, 0L};
            }

            fa = fb;
            fb = next;
        }
    }

    static void test(final long prod) {
        System.out.println("prod = " + prod);
        System.out.println(Arrays.toString(productFib(prod)));
    }

    public static void main(String[] args) {
        test(714);
        test(800);
    }
}
