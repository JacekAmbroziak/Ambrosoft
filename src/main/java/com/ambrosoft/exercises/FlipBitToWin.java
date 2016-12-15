package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/14/16.
 */
public class FlipBitToWin {
    /*
        brute force? How to measure lengths of groups of 1s?
        Brute force would try to flip every 0 bit an check for max-len group
     */

    private static int groups(int n, int[] lengths) {
        int bit = n & 1;    // bit of last group
        int length = 1;     // count it
        int index = bit != 0 ? 1 : 0;   // normalize to start w/ counting zeros
        for (int shift = 0; shift < 31; ++shift) {
            n >>>= 1;   // shift logical right once
            if ((n & 1) == bit) {   // same?
                ++length;
            } else {    // change
                lengths[index++] = length;
                length = 1; // reset: count 1st changed bit
                bit = n & 1;// the changed bit (could also be 1 - bit)
            }
        }
        lengths[index++] = length;
        return index;
    }

    static int longestAfterFlip(final int N) {
        if (N == 0) {   // edge cases not only provide direct answer fast, but the algo approach below doesn't work in their cases
            return 1;
        } else if (N == -1) {
            return Integer.BYTES * Byte.SIZE;
        } else {    // after eliminating all 0s and all 1s, we are sure to have a least 2 groups
            // extend longest group of 1s, or join 2 groups of 1s looking for pattern 11111011111
            // such pattern will have solitary 1s in negated form
            // find groups

            final int[] lengths = new int[32];
            final int nGroups = groups(N, lengths);

            System.out.println("count = " + nGroups);
            System.out.println("lenghts = " + Arrays.toString(lengths));

            int max = 0;
            // look at all groups of zeros if one zero next to a group of ones can be flipped
            for (int i = 0; i < nGroups; i += 2) {
                if (lengths[i] == 0) {    // possibly first or last group
                    // can't flip anything
                } else if (lengths[i] == 1) {   // joining two groups of 1s?
                    int ones = 1;   // current zero flipped
                    if (i > 0) {
                        ones += lengths[i - 1];
                    }
                    if (i + 1 < nGroups) {
                        ones += lengths[i + 1];
                    }
                    if (ones > max) {
                        max = ones;
                    }
                } else {    // longer group, no joining, only extending left or right
                    if (i > 0) {
                        max = Math.max(max, lengths[i - 1] + 1);
                    }
                    if (i + 1 < nGroups) {
                        max = Math.max(max, lengths[i + 1] + 1);
                    }
                }
            }
            return max;
        }
    }

    static void test(int N) {
        System.out.println("N = " + Integer.toBinaryString(N));
        System.out.println("longestAfterFlip(N) = " + longestAfterFlip(N));
    }

    public static void main(String[] args) {
        test(0);
        test(1);
        test(1775);
        test(0xF000000F);
        test(-1);
    }
}
