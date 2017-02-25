package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/28/16.
 */
public class MajorityElement {
    // find an element among positive integers with # of occurrences > half the set cardinality
    // if such an element exists, there can be only one
    // desired time O(n), space O(1) so at most constant # of passes
    // if more than 1/2 of the elements is x, many x's have to be consecutive, or every other
    // deltas will be 0, or +k -k  => sum of deltas will be small

    static void compute(int[] a) {
        int[] d = new int[a.length];
        for (int i = 1; i < a.length; i++) {
            d[i] = a[i] - a[i - 1];
        }

        int sum = 0;
        for (int i = 1; i < d.length; i++) {
            sum += d[i];
        }

        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(d));
        System.out.println("sum = " + sum);
    }

    // Gayle
    // pure magic...
    // it kind of eliminates non-candidates

    static int getCandidate(int[] a) {
        int candidate = 0;
        int matches = 0;    // node of matches of current candidate
        for (int n : a) {
            // if matches should reach 0, the current candidate is not a majority element in prefix explored thus far
            // it is OK to first considering another value
            // ... so, dropping candidates which are not majority elements in prefixes
            // "not conclusive thus far..."
            // final value of candidate will be the value last set
            // almost a random value if no majority present, eg. all distinct elements
            // but a successful candidate will "march on" on the virtue of strong incremental statistics
            // example: 3, 2, 5, 9, 5, 9, 5, 5, 5
            // one can reason that if 5 is majority here, then there is no permutation that would hide this fact
            // from the simple mechanism of "current candidate & it's counter"
            // eg. when all 5's are at the beginning they will elevate the counter high enough that future mismatches will not hurt it
            // counter dropping to 0 for any prefix only means: "no majority so far"
            // if majority element exists, it will have to be the majority in the suffix
            // prefix:suffix reasoning works ok
            // one can consider prefix, suffix, and prefix+suffix as independent arrays
            // where the division is wherever counter drops to 0
            if (matches == 0) {
                candidate = n;
            }
            if (n == candidate) {
                ++matches;
            } else {
                --matches;
            }
        }
        return candidate;
    }

    static boolean validate(int[] a, int candidate) {
        final int majority = a.length / 2 + 1;
        int count = 0;
        for (int i = a.length; --i >= 0; ) {
            if (a[i] == candidate) {
                if (++count == majority) {
                    return true;
                }
            }
        }
        return false;
    }

    static int findMajority(int[] a) {
        final int candidate = getCandidate(a);
        return validate(a, candidate) ? candidate : -1;
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println("getCandidate(a) = " + getCandidate(a));
        System.out.println("findMajority(a) = " + findMajority(a));
    }

    public static void main(String[] args) {
        test(new int[]{3, 2, 5, 9, 5, 9, 5, 5, 5});
//        compute(new int[]{1, 2, 3, 4, 5, 6, 7});
//        compute(new int[]{7, 6, 5, 4, 3, 2, 1});

//        test(new int[]{4, 4, 5, 4, 5, 4, 5, 4, 5});
        test(new int[]{4, 4, 4, 1, 4, 2, 4, 3, 5, 6, 4});
//        compute(new int[]{4, 5, 4, 5, 4, 5, 4, 5, 4});
//        compute(new int[]{4, 5, 4, 5, 4, 5, 4, 5, 5});
//        compute(new int[]{4, 4, 4, 4, 5, 6, 7});
//        compute(new int[]{4, 4, 4, 4, 5, 6, 7, 8});
    }
}
