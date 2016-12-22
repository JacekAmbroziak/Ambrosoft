package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/18/16.
 */
public class LeastDisruptiveReplacement {
    // given original array of ints and a shorter replacement array
    // find where the replacement can be applied minimizing the total change to original
    /*
        after replacement the patch will be a substring of the modified string
        we are just trying to find the spot minimizing the change

        brute force is straightforward, but can we do better?
            compute sum of patch, running sum of window, skip candidate windows that look worse
            can this idea of incremental updates be reused?
            we could apply the patch and keep the deltas
            now, shift the patch to the right: can we update deltas fast?
     */

    static int replace(int[] a, int[] b) {
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        return replaceBruteForce(a, b);
    }

    private static int distance(int[] a, int ai, int[] b, int bi) {
        int dist = 0;
        while (ai < a.length && bi < b.length) {
            dist += Math.abs(a[ai++] - b[bi++]);
        }
        return dist;
    }

    private static int[] subarray(int[] a, int start, int length) {
        final int[] result = new int[length];
        System.arraycopy(a, start, result, 0, length);
        return result;
    }

    static int replaceBruteForce(int[] a, int[] b) {
        final int alen = a.length;
        final int blen = b.length;

        int index = 0;
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < alen - blen; i++) {
            int dist = distance(a, i, b, 0);
            if (dist < minDist) {
                index = i;
                minDist = dist;
            }
        }
        System.out.println("minDist = " + minDist);
        return index;
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(20, 50);
        int[] b = Utils.createRandomArray(5, 50);
        int index = replace(a, b);
        System.out.println("index = " + index + "\t" + Arrays.toString(subarray(a, index, b.length)));
    }
}
