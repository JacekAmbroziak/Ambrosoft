package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/5/16.
 */
public class DivisibleBy1to20 {

    /*
    Find smallest number divisible by [1,20]
    Must be a product of all primes < 20
    plus a few small primes to support divisibility by eg. 18,16
     */

    public static void main(String[] args) {
        int n = 19 * 17 * 13 * 11 * 7 * 5 * 3 * 2 * 3 * 2 * 2 * 2;
        System.out.println("n = " + n);
        System.out.println("isdiv = " + isDivisible(n, 20));

        search();
    }

    static boolean isDivisible(final int n, int divisor) {
        while (divisor > 1) {
            if (n % divisor-- != 0) {
                System.out.println("largestDivisor = " + (divisor + 1));
                return false;
            }
        }
        return true;
    }

    static int isDivisible2(final int n, int divisor) {
        while (divisor > 1) {
            if (n % divisor-- != 0) {
                return divisor + 1;
            }
        }
        return divisor;
    }

    static int firstNonDivisor(final int n) {
        System.out.println("n = " + n);
        for (int divisor = 2; divisor < n; ++divisor) {
            if (n % divisor != 0) {
                return divisor;
            }
        }
        return n;
    }

    static void search() {

        int n = 19 * 17 * 13 * 11 * 7 * 5 * 3 * 2;

        // starting point for n, has to have these factors at least

        System.out.println("f1 = " + isDivisible2(n, 20));
        System.out.println("non div = " + firstNonDivisor(n));
        System.out.println("non div = " + firstNonDivisor(n*4));
        System.out.println("non div = " + firstNonDivisor(n*4*3*2));
//        System.out.println("f1 = " + isDivisible2(20*19*18*17*16*14*13*11, 20));


    }
}
