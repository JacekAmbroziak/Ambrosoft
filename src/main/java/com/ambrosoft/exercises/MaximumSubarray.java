package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/31/16.
 */
public class MaximumSubarray {

    // nice property of this problem is symmetry: max sum is the same for reversed array

    // looking for subarray with biggest sum
    // divide and conquer: max subarray in the left half, right half or straddling midpoint

    // in all cases first is inclusive (first index) and last is exclusive (beyond last index)

    static class Interval {
        final int start;    // inclusive
        final int end;      // exclusive
        int sum;

        Interval(int start, int end, int sum) {
            this.start = start;
            this.end = end;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + ") = " + sum;
        }
    }

    private static Interval crossing(int[] a, int start, int end, int mid) {
        // grow left
        int start1 = mid, lsum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = mid; --i >= start; ) {
            lsum += a[i];
            if (lsum > max) {
                max = lsum;
                start1 = i;
            }
        }
        lsum = max;
        // grow right
        int rsum = 0, end1 = mid;
        max = Integer.MIN_VALUE;
        for (int i = mid; i < end; ++i) {
            rsum += a[i];
            if (rsum > max) {
                max = rsum;
                end1 = i;
            }
        }
        rsum = max;
        return new Interval(start1, end1 + 1, lsum + rsum);
    }

    private static Interval findMaximumSubarray(int[] a, int start, int end) {
        final int len = end - start;
        switch (len) {
            case 0:
                throw new Error();
            case 1:
                return new Interval(start, end, a[start]);
            default:
                final int mid = start + len / 2;
                final Interval lft = findMaximumSubarray(a, start, mid);
                final Interval rgt = findMaximumSubarray(a, mid, end);
                final Interval ctr = crossing(a, start, end, mid);

                if (lft.sum >= rgt.sum && lft.sum >= ctr.sum) {
                    return lft;
                }
                if (rgt.sum >= lft.sum && rgt.sum >= ctr.sum) {
                    return rgt;
                }
                return ctr;
        }
    }

    static Interval findMaximumSubarray(int[] a) {
        return findMaximumSubarray(a, 0, a.length);
    }

    // linear solution
    // looking at array prefixes:
    // max sub for a[0..j+1] is either the same as for a[0..j] or it is a[i..j+1]

    static Interval linear(int[] a) {
        final int len = a.length;

        Interval best = new Interval(0, 1, a[0]);

        int sum = 0;
        int i = 0;

        for (int j = 0; j < len; j++) {

        }


        return new Interval(0, len, 0);
    }

    static int Kadane(int[] a) {
        int maxSoFar = 0, maxEndingHere = 0;
        for (int i = 0; i < a.length; i++) {
            maxEndingHere = Math.max(maxEndingHere + a[i], 0);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    // augmentation to also keep track of coordinates of the winning interval
    static Interval Kadane2(int[] a) {
        int maxSoFar = 0, maxEndingHere = 0;
        int start = 0, end = 0, maxstart = 0;
        for (int i = 0; i < a.length; i++) {
            final int attemptAtGrowthExtension = maxEndingHere + a[i];
            if (attemptAtGrowthExtension > 0) {
                if (maxEndingHere == 0) {
                    start = i;  // store first of growing seq
                }
                maxEndingHere = attemptAtGrowthExtension;
                if (maxEndingHere > maxSoFar) { // also improving best so far?
                    maxSoFar = maxEndingHere;
                    maxstart = start;
                    end = i + 1;
                }
            } else {
                maxEndingHere = 0;
            }
        }
        return new Interval(maxstart, end, maxSoFar);
    }

    // total, cubic brute force w/o smarter interval sum calculation
    static Interval bruteForce(int[] a) {
        int maxsum = Integer.MIN_VALUE;
        int maxstart = -1, maxend = -1;
        for (int start = 0; start < a.length; start++) {
            for (int end = start + 1; end <= a.length; end++) {
                int sum = 0;
                for (int k = start; k < end; k++) {
                    sum += a[k];
                }
                if (sum > maxsum) {
                    maxsum = sum;
                    maxstart = start;
                    maxend = end;
                }
            }
        }
        return new Interval(maxstart, maxend, maxsum);
    }

    static void test(final int[] a) {
        System.out.println();
        System.out.println(Arrays.toString(a));
        Interval result = findMaximumSubarray(a);
        System.out.println("result = " + result);
        System.out.println("bruteForce(a) = " + bruteForce(a));
        System.out.println("Kadane2(a) = " + Kadane2(a));

    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(20);
        Utils.limitArray(a, 100);
        test(a);
        test(Utils.reverseArray(a));
//
        test(new int[]{2, 2, -3, 5});
        test(new int[]{1, 2, 3});
        test(new int[]{2, 2, -5, 2, 3});
        test(new int[]{4, 2, -5, 2, 3});
        test(new int[]{-1, -2, -3});
        test(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
    }
}
