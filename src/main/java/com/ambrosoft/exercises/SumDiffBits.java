package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 10/9/16.
 * http://www.geeksforgeeks.org/sum-of-bit-differences-among-all-pairs/#disqus_thread
 * <p>
 * Given an integer array of n integers, find sum of bit differences in all pairs that can be formed from array elements.
 * Bit difference of a pair (x, y) is count of different bits at same positions in binary representations of x and y.
 * For example, bit difference for 2 and 7 is 2.
 * Binary representation of 2 is 010 and 7 is 111 ( first and last bits differ in two numbers).
 * <p>
 * surprising: no need to look at all pairs!
 */
public class SumDiffBits {
    static int[] counts = new int[256];

    static {
        for (int i = 1; i < 256; i++) {
            counts[i] = countBits8(i);
        }
    }

    private static int countBits8(final int eight) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            if ((eight >>> i & 0x01) != 0) {
                ++sum;
            }
        }
        return sum;
    }

    static int countBits32(final int n) {
        return counts[n & 0xFF] + counts[(n >> 8) & 0xFF] + counts[(n >> 16) & 0xFF] + counts[(n >> 24) & 0xFF];
    }

    static long sumNaive(final int[] a) {
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                sum += countBits32(a[i] ^ a[j]);
            }
        }
        return sum;
    }

    /*
        can consider counting bit position by bit position
        if we should consider only 1-bit numbers, the number of differences can be computed directly
        if we know how many 1's we have: each 1 contributes |0's| differences
        so total # of differences is |1's|*|0's|
     */
    static long fastSum(final int[] a) {
        long sum = 0;
        long nOnes = 0;
        for (int i = 0, mask = 1; i < 32; ++i, mask <<= 1) {
            for (int j = 0; j < a.length; j++) {
                if ((a[j] & mask) != 0) {
                    ++nOnes;
                }
            }
            sum += nOnes * (a.length - nOnes);
            nOnes = 0;
        }
        return sum;
    }

    public static void main(String[] args) {
        int xor37 = 4 ^ 3;
        System.out.println("xor37 = " + xor37);
        System.out.println("four = " + countBits32(0x0F00));
        System.out.println("six = " + countBits32(0xF003));

        final Random rand = new Random(System.currentTimeMillis());

        int[] a = new int[100];
        for (int i = a.length; --i >= 0; ) {
            a[i] = rand.nextInt();
        }

        System.out.println("sum = " + sumNaive(new int[]{1, 2, 3, 4, 5}));
        System.out.println("sum = " + fastSum(new int[]{1, 2, 3, 4, 5}));

        System.out.println("sumNaive(a) = " + sumNaive(a));
        System.out.println("fastSum(a) = " + fastSum(a));
    }
}
