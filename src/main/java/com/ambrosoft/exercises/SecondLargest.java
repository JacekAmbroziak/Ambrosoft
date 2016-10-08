package com.ambrosoft.exercises;

/**
 * Created by jacek on 8/2/16.
 */
public class SecondLargest {

    /*
    You are given as input an unsorted array of n distinct numbers, where n is a power of 2.
    Give an algorithm that identifies the second-largest number in the array,
    and that uses at most n+log2n−2 comparisons.

     */

    public static void main(String[] args) {
        System.out.println("second = " + secondLargest(new int[]{4, 3, 2, 1}));
        System.out.println("second = " + secondLargest(new int[]{4, 3}));
    }

    static int secondLargest(int[] a) {
        switch (a.length) {
            case 0:
            case 1:
                throw new IllegalArgumentException();

            case 2:
                return Math.min(a[0], a[1]);

            default:
                final int largest = largest(a, 0, a.length);
                int second = Integer.MIN_VALUE;
                // scan all searching for max other than largest
                for (int i = 0; i < a.length; ++i) {
                    if (a[i] > second && a[i] != largest) {
                        second = a[i];
                    }
                }

                return second;
        }
    }

    private static int largest(int[] a, int lo, int hi) {
        final int len = hi - lo;
        if (len == 2) {
            return Math.max(a[lo], a[lo + 1]);
        } else {
            final int mid = lo + len / 2;
            return Math.max(largest(a, lo, mid), largest(a, mid, hi));
        }
    }


}
