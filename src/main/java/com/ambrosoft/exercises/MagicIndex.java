package com.ambrosoft.exercises;

/**
 * Created by jacek on 7/6/16.
 */
public class MagicIndex {

    // find i satisfying A[i] == i for sorted array of distinct integers
    /*
        for integers 0..n-1 every index is magic
        magic index can only be >=0 & < n
        sorted: A[i] grows with i
        so if A[i] >= n, there is no chance for MI from i upwards
        e.g. when A[0] >= n, there is no chance
            even stronger: when A[i] > i, no chance i..n-1
            so: if  A[0] > 0, no solution
        (mischievous case: consecutive from -1)
        if A[i] < i, VALUES must have started from negative
     */


    public static void main(String[] args) {
        int[] a = new int[100];
        int val = -30;
        for (int i = 0; i < a.length; i++) {
            a[i] = val;
            val += 2;
        }

        int magic = findMagic(a);
        System.out.println("magic = " + magic);

    }

    static int findMagic(final int[] a) {
        if (a == null || a.length == 0) {
            return -1;
        } else if (a[0] > 0) {  // indexes will never catch up
            return -1;
        } else {
            final int n = a.length;
            if (a[n - 1] < n - 1) { // all VALUES too small
                return -1;
            } else {
                return search(a, 0, n);
            }
        }

    }

    private static int search(int[] a, int lo, int hi) {
        if (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            if (a[mid] == mid) {
                return mid;
            } else if (a[mid] > mid) {
                return search(a, lo, mid);
            } else {
                return search(a, mid, hi);
            }
        } else {
            return -1;
        }
    }
}
