package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jacek on 10/18/16.
 */

public class LongestIncreasingSubsequence {


    // intuition: prefer smaller endings -- more extension potential
    // it is enough to store only the latest seq found
    // each value extends some sequence, perhaps the empty seq
    // progress: extension of longest so far (which creates new longest)
    // or replacement of existing with one with better extension potential

    static int longestIncreasingSubsequenceLength(final int[] a) {
        final int n = a.length;
        // keep track of last elements of sequences indexed by seq length
        final int[] lastVal = new int[n + 1];

        int maxlen = 0; // empty sequence
        lastVal[0] = Integer.MIN_VALUE; // ... so that the empty sequence can always be extended

        for (int i = 0; i < n; i++) {
            final int value = a[i];
            // search back for longest seq that can be extended
            for (int len = maxlen; len >= 0; --len) {
                if (value > lastVal[len]) { // seq of length len can be extended
                    lastVal[len + 1] = value;   // extend it by storing seq of length len+1
                    if (len == maxlen) {
                        maxlen = len + 1;   // we have a new maxlen
                    }
                    break;
                }
            }
        }
        return maxlen;
    }

    static int longestIncreasingSubsequenceLength2(final int[] a) {
        final int n = a.length;
        // keep track of last elements of sequences indexed by seq length
        final int[] lastVal = new int[n + 1];

        int maxlen = 0; // empty sequence
        lastVal[0] = Integer.MIN_VALUE; // ... so that the empty sequence can always be extended

        for (int i = 0; i < n; i++) {
            final int value = a[i];
            if (value > lastVal[maxlen]) {
                lastVal[++maxlen] = value;
            } else {
                // search back for longest seq that can be extended
                for (int len = maxlen; --len >= 0; ) {
                    if (value > lastVal[len]) { // seq of length len can be extended
                        lastVal[len + 1] = value;   // extend it by storing seq of length len+1
                        if (len == maxlen) {
                            maxlen = len + 1;   // we have a new maxlen
                        }
                        break;
                    }
                }
            }
        }
        return maxlen;
    }

    static int[] lis(final int[] a) {
        final int n = a.length;
        final int[] indexOfLast = new int[n + 1];  // indexed by length of that sequence
        final int[] predecessor = new int[n];

        int maxlen = 1;
        indexOfLast[1] = 0;  // initial seq of just the 1st element at index 0

        // continue with remaining values
        for (int i = 1; i < n; i++) {
            final int value = a[i];
            // search back for the longest seq that can be extended with val
            // some seq will always be extended: the longest, a non-empty seq, or empty

            // start with border cases

            if (value > a[indexOfLast[maxlen]]) {
                predecessor[i] = indexOfLast[maxlen];
                indexOfLast[++maxlen] = i;  // extend the longest seq and update maxlen
            } else if (value < a[indexOfLast[1]]) {
                indexOfLast[1] = i;   // extend empty sequence: starting new seq of length 1
            } else {
                for (int len = maxlen; --len >= 1; ) {
                    if (value > a[indexOfLast[len]]) {
                        predecessor[i] = indexOfLast[len];
                        indexOfLast[len + 1] = i;   // extend seq of length len
                        break;
                    }
                }
            }
        }

        System.out.println("maxlen " + maxlen);

        final int[] seq = new int[maxlen];
        for (int i = maxlen, idx = indexOfLast[maxlen]; --i >= 0; idx = predecessor[idx]) {
            seq[i] = a[idx];
        }
        return seq;
    }

    static int[] lisFast(final int[] a) {
        final int n = a.length;
        final int[] indexOfLast = new int[n + 1];  // indexed by length of that sequence
        final int[] predecessor = new int[n];

        int maxlen = 1;
        indexOfLast[1] = 0;  // initial seq of just the 1st element at index 0

        // continue with remaining values
        for (int i = 1; i < n; i++) {
            final int value = a[i];
            // search back for the longest seq that can be extended with val
            // some seq will always be extended: the longest, a non-empty seq, or empty

            // start with border cases

            if (value > a[indexOfLast[maxlen]]) {
                predecessor[i] = indexOfLast[maxlen];
                indexOfLast[++maxlen] = i;  // extend the longest seq and update maxlen
            } else if (value < a[indexOfLast[1]]) {
                indexOfLast[1] = i;   // extend empty sequence: starting new seq of length 1
            } else {
                for (int len = maxlen; --len >= 1; ) {
                    if (value > a[indexOfLast[len]]) {
                        predecessor[i] = indexOfLast[len];
                        indexOfLast[len + 1] = i;   // extend seq of length len
                        break;
                    }
                }
            }
        }

        System.out.println("maxlen " + maxlen);

        final int[] seq = new int[maxlen];
        for (int i = maxlen, idx = indexOfLast[maxlen]; --i >= 0; idx = predecessor[idx]) {
            seq[i] = a[idx];
        }
        return seq;
    }

    static void test(int a[]) {
        System.out.println(Arrays.toString(a));
        System.out.println(">>>>");
        System.out.println(Arrays.toString(lis(a)));
        System.out.println("lisLength(a) = " + longestIncreasingSubsequenceLength(a));
        System.out.println("lisLength2(a) = " + longestIncreasingSubsequenceLength2(a));
        System.out.println();
    }


    // a: sorted, nonempty array
    // result: index of the biggest entry that is smaller than val
    // or -1 iff no entry is smaller
    static int indexOfSmaller(final int[] a, final int val) {
        if (a[0] >= val) {
            return -1;  // no entry is smaller
        } else {
            int lo = 0, hi = a.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (val > a[mid]) {
                    lo = mid + 1;
                } else if (val < a[mid]) {
                    hi = mid - 1;
                } else {    // val found at mid
                    // scan down guaranteed to terminate as we know at least one value is < val
                    while (a[--mid] >= val) {
                    }
                    return mid;
                }
            }
            // when value not found and above loop ends with lo > hi, a[hi] is largest smaller than val
            return hi;
        }
    }

    static void testBinarySearch() {
        final Random random = new Random(System.nanoTime());
        for (int i = 100000; --i >= 0; ) {
            int length = random.nextInt(10000) + 1;
            int bound = length;

            int[] a = Utils.createRandomArray(length, bound);
            Arrays.sort(a);
            int value = random.nextInt(a[length - 1] + 1);

            // find max element < number

            int index1 = -1;
            for (int j = a.length; --j >= 0; ) {
                if (a[j] < value) {
                    index1 = j;
                    break;
                }
            }

            int index2 = indexOfSmaller(a, value);

            if (index1 != index2 || index1 >= 0 && value <= a[index1]) {
                throw new Error();
            }

        }

        System.out.println("tested OK");
    }


    public static void main(String[] args) {

        testBinarySearch();

        int[] a = Utils.createRandomArray(80, 100);
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        int hi = indexOfSmaller(a, 50);
        System.out.println("hi = " + hi);
        if (hi >= 0) {
            System.out.println("a[hi] = " + a[hi]);
        }

//        test(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15});
//        test(new int[]{4, 3, 8, 4, 10, 12, 5, 6, 7});
//        test(new int[]{4, 3, 2, 4});
//        test(new int[]{1, 2, 3});
//        test(new int[]{1});
//        test(new int[]{2, 6, 3, 4, 1, 2, 7, 9, 5, 8});
//        test(Utils.createRandomArray(20, 20));
//        System.out.println("lisLength(new int[]{}) = " + longestIncreasingSubsequenceLength(new int[]{}));
//        System.out.println("lisLength(new int[]{}) = " + longestIncreasingSubsequenceLength(new int[]{7}));
//        System.out.println("lisLength(new int[]{}) = " + longestIncreasingSubsequenceLength(new int[]{7, 6}));
//        System.out.println("lisLength(new int[]{}) = " + longestIncreasingSubsequenceLength(new int[]{7, 8}));
//        System.out.println("lisLength(new int[]{}) = " + longestIncreasingSubsequenceLength2(new int[]{7, 8}));
    }
}
