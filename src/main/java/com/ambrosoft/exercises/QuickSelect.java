package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jacek on 12/1/16.
 */

public class QuickSelect {
    /*
        O(n) algorithm to find an element bigger than k elements of an array
        a generalization of finding minimal/maximal element, can find median or other percentile
        halving interval & recursing on both like quicksort leads to O(n lg n)
        recursing on just one half leads to O(n)

        Partitioning around an element provides info on how many elements ar smaller than this element
        if that count is bigger than k, we can repeat the process in the left part, containing k
        Each time we eliminate elements guaranteed not to be smaller than k-th
     */

    private static int partition(final int[] a, final int lo, final int hi, final Random random) {
        // chose random element from [lo, hi]
        final int randomIndex = lo + random.nextInt(hi - lo + 1);
        final int pivot = a[randomIndex];
        a[randomIndex] = a[lo];
//        a[lo] = pivot;

        int m = lo;
        // look at all elements after a[lo]
        for (int i = lo + 1; i <= hi; ++i) {
            if (a[i] < pivot) {
                final int temp = a[++m];
                a[m] = a[i];
                a[i] = temp;
            }
        }
        a[lo] = a[m];
        a[m] = pivot;
        return m;
    }

    static int select(final int[] a, final int k) {
        final Random random = new Random(System.currentTimeMillis());   // reusable Random
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            final int m = partition(a, lo, hi, random);
            if (k == m) {
                return a[k];
            } else if (k < m) {
                hi = m - 1;
            } else {
                lo = m + 1;
            }
        }
        return a[lo];
    }

    static int viaSort(int[] a, int k) {
        final int[] copy = Arrays.copyOf(a, a.length);
        Arrays.sort(copy);
        return copy[k];
    }

    static void test(int[] a, int k) {
        System.out.println(Arrays.toString(a));

        int nLgN = viaSort(a, k);

//        int m = partition(a, 0, a.length - 1);
//        System.out.println(Arrays.toString(a));
//        System.out.println("m = " + m);
//        System.out.println("a = " + a[m]);

        int val = select(a, k);
        System.out.println("val = " + val);
        System.out.println("nLgN = " + nLgN);
        System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(20, 20);
        test(a, 1);

        int[] b = new int[20];
        for (int i = 0; i < b.length; i++) {
            b[i] = i + i;
        }
        System.out.println("----");
        System.out.println(Arrays.toString(b));
//        Utils.shuffle(b);
//        System.out.println(Arrays.toString(b));
        test(b, 3);
    }
}
