package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 8/2/16.
 */
public class SecondLargest {

    /*
    You are given as input an unsorted array of n distinct numbers, where n is a power of 2.
    Give an algorithm that identifies the second-largest node in the array,
    and that uses at most n+log2n−2 comparisons.

    'largest' could also return index of largest to help with duplicates
     */

    public static void main(String[] args) {
        System.out.println("second = " + secondLargest(new int[]{4, 3, 2, 1}));
        System.out.println("second = " + secondLargest(new int[]{4, 3}));
        System.out.println("second = " + secondLargest(new int[]{4, 1, 1, 1, 1, 1}));
        System.out.println("second = " + secondLargest(new int[]{4, 4, 4, 4}));

        int a[] = Utils.createRandomArray(30, 100);
        System.out.println("secondLargest(a) = " + secondLargest(a));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
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
                for (int i = a.length; --i >= 0; ) {
                    if (a[i] > second && a[i] != largest) {
                        second = a[i];
                    }
                }
                return second;
        }
    }

    private static int largest(int[] a, int start, int end) {
        final int len = end - start;
        switch (len) {
            case 1:
                return a[start];

            case 2:
                return Math.max(a[start], a[start + 1]);

            default:
                final int mid = start + len / 2;
                return Math.max(largest(a, start, mid), largest(a, mid, end));
        }
    }
}
