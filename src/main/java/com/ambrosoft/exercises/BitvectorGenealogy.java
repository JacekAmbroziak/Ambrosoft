package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/29/16.
 */
public class BitvectorGenealogy {
    /*
        The BitVectors are an ancient and immortal race of 10,000, each with a 10,000 bit genome.
        The race evolved from a single individual by the following process:
        9,999 times a BitVector chosen at random from amongst the population was cloned
        using an error-prone process that replicates each bit of the genome with 80% fidelity
        (any particular bit is flipped from parent to child 20% of the time, independent of other bits).

        How they evolved:
        1) start with original, create its first son
        2) select randomly original or 1st son, generate another
        3) in the beginning the early family is small, some members can be used multiple times
        -- chances of original being selected: 1 + 1/2 + 1/3 + 1/4 + ... + 1/9999 ~ 10 (only!)
        4) gradually chances are falling for any particular member to be selected as parent
        5) parent-son relationship: 80% bits the same
        6) 2nd generation: flip 1/5*1/5 bits back to grandparent and 1/5*4/5 away
        7) example: 1000 bits - son 800 same, 200 negated
        -- grandson 640 from orig, 160 new negated, 160 prev negated, 40 reverted to grandparent
        -- so 640 original + 40 reverted (680 from grandparent), 160+160=320 negated

        It would be interesting to identify pairs: parent-son edges, but quadratic
         One could also count 1's and sort
         Maybe use a mask to concentrate on a selected 'band' to find similarity
     */

    static int[] BIT_COUNTS = new int[256];

    static {
        for (int i = BIT_COUNTS.length; --i > 0; ) {
            int count = 0;
            for (int j = 0; j < Byte.SIZE; ++j) {
                count += (i >>> j) & 0x01;
            }
            BIT_COUNTS[i] = count;
        }
    }

    static final class BitVector {
        final long[] data;

        BitVector(int capacity) {
            data = new long[capacity / Long.SIZE + 1];
        }
    }

    static double harmonicSum(int n) {
        double sum = 0.0;
        for (int i = 1; i < n; ++i) {
            sum += 1.0 / i;
        }
        return sum;
    }

    public static void main(String[] args) {
        double sum = harmonicSum(10000);
        System.out.println("sum = " + sum);

        BitVector bv = new BitVector(10000);
        System.out.println();
    }
}
