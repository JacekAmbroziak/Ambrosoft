package com.ambrosoft.exercises;

import java.math.BigInteger;

/**
 * Created by jacek on 10/7/16.
 */
public class LatticePaths {

    /*
        to walk the lattice corner to corner we need n D (down) steps & n R steps
        for 2x2 we need all permutations of DDRR (there are 6 (n1 + n2)!/(n1! * n2!)

        for 20x20  it is 40!/(20! * 20!) or 21* ... *40/1* ... *20


        strangely, my first method w/ recurrence doesn't work
     */


    public static void main(String[] args) {
        System.out.println("res = " + recurrence(20));
        System.out.println("prod = " + product(21, 40).divide(product(1, 20)));
    }

    static int recurrence(int n) {
        if (n == 1) {
            return 2;
        } else {
            return 2 * recurrence(n - 1) + 2 * (n - 1);
        }
    }

    static BigInteger product(int first, int last) {
        BigInteger bi = BigInteger.ONE;
        for (long i = first; i <= last; ++i) {
            bi = bi.multiply(BigInteger.valueOf(i));
        }
        return bi;
    }

    static long fact(long n) {
        return n == 2 ? 2 : fact(n - 1) * n;
    }
}
