package com.ambrosoft.exercises;

/**
 * Created by jacek on 8/2/16.
 */
public class Unimodal {
    /*
    You are a given a unimodal array of n distinct elements,
    meaning that its entries are in increasing order up until its maximum element,
    after which its elements are in decreasing order.
    Give an algorithm to compute the maximum element that runs in O(log n) time.
     */

    /*
    It is possible for max element to be first or last

    Halving: look at an element & compare with directly preceding
     */

    public static void main(String[] args) {
        int max = unimodalMax(new int[]{1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 1});
        System.out.println("max = " + max);
    }

    static int unimodalMax(int[] a) {
        return unimodalMax(a, 0, a.length);
    }

    private static int unimodalMax(final int[] a, final int lo, final int hi) {
        final int len = hi - lo;
        switch (len) {
            case 0:
                throw new AssertionError();

            case 1:
                return a[lo];

            case 2:
                return Math.max(a[lo], a[lo + 1]);

            case 3:
                return Math.max(Math.max(a[lo], a[lo + 1]), a[lo + 2]);

            default:    // len >=4
                final int mid = lo + len / 2;
                if (a[mid - 1] < a[mid]) {  // still increasing
                    return unimodalMax(a, mid, hi);
                } else {
                    return unimodalMax(a, lo, mid - 1);
                }
        }
    }
}
