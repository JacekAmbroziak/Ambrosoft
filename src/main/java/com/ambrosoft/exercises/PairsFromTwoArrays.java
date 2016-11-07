package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/29/16.
 */
public class PairsFromTwoArrays {

    static int pairCount1(int[] a, int[] b, int c) {
        int count = 0;
        // brute force, good for testing
        for (int i : a) {
            for (int j : b) {
                if (i + j <= c) {
                    ++count;
                }
            }
        }
        return count;
    }

    // bad concept!
    static int pairCount2(int[] a, int[] b, int c) {
        // by destructive sorting
        Arrays.sort(a);
        Arrays.sort(b);

        int i = a.length;
        while (--i >= 0 && a[i] + b[0] > c) {
            ++i;
        }
        int j = b.length;
        while (--j >= 0 && a[0] + b[j] > c) {
            ++j;
        }
        return i * j;
    }

    static int pairCount3(int[] a, int[] b, int c) {
        // by destructive sorting O(n log n)
        Arrays.sort(a);
        Arrays.sort(b);

        final int alen = a.length;
        final int blen = b.length;

        // first sanity checks: fast results & justification for non-empty search 'else'
        if (a[0] + b[0] > c) {
            return 0;   // no pairs
        } else if (a[alen - 1] + b[blen - 1] <= c) {
            return alen * blen; // all pairs
        } else {
            int i = 0;  // always growing index for a
            int j = blen - 1;   // always descending index for b

            // for a[0] find biggest b[j] such that a[0] + b[j] <= c
            // a[0] can form pairs with all elements b[0..j] (j+1 pairs)
            // we then try a[1] by finding a smaller b[j]
            int count = 0;
            do {
                while (a[i] + b[j] > c) {
                    if (--j < 0) {
                        return count;
                    }
                }
                count += j + 1;
            } while (++i < alen);
            return count;
        }
    }

    static void test(int[] a, int[] b, int c) {
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println("c = " + c);
        System.out.println("pairCount1(a,b,c) = " + pairCount1(a, b, c));
//        System.out.println("pairCount2(a,b,c) = " + pairCount2(a, b, c));
        System.out.println("pairCount3(a,b,c) = " + pairCount3(a, b, c));
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(10000);
        int[] b = Utils.createRandomArray(10000);
        Utils.limitArray(a, 1000);
        Utils.limitArray(b, 1000);
        int c = (a[0] + b[0]) / 2;
        test(a, b, c);
    }
}
