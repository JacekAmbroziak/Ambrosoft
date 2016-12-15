package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/15/16.
 */
public class PairwiseSwap {
    static final int ALTERNATING_MASK = 0b01010101010101010101010101010101;

    // swap bits 0-1, 2-3, etc.

    static int swapOddEvenBits(int N) {
        System.out.println("N = " + Integer.toBinaryString(N));

        // approach: take bits from input shifted left & input shifted right
        // using an alternating mask & its negation to select from one or the other

        int rgt = (N >>> 1) & ALTERNATING_MASK;
        int lft = (N << 1) & ~ALTERNATING_MASK;

        return lft | rgt;
    }

    public static void main(String[] args) {
        System.out.println("result = " + Integer.toBinaryString(swapOddEvenBits(10)));
        System.out.println("result = " + Integer.toBinaryString(swapOddEvenBits(6)));
        System.out.println("result = " + Integer.toBinaryString(swapOddEvenBits(3)));
    }
}
