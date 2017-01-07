package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/6/17.
 */
public class PeaksAndValleys {
    /*
        arrange elements of an int array into consecutive peaks and valleys
        because we assume <= such arrangement is always possible, even with all equal values

        My approach: O(n lg n): sort array, pick consecutive from either end
        using temp array for simplicity, doable in place

        More clever approach: linear.
        Also no need for separate isPeak/isValley: peaks every other mean valleys between them
     */


    private static boolean isPeak(int[] a, int index) {
        if (index == 0) {
            return a[0] >= a[1];
        } else if (index == a.length - 1) {
            return a[index] >= a[index - 1];
        } else {
            return a[index] >= a[index - 1] && a[index] >= a[index + 1];
        }
    }

    private static boolean isValley(int[] a, int index) {
        if (index == 0) {
            return a[0] <= a[1];
        } else if (index == a.length - 1) {
            return a[index] <= a[index - 1];
        } else {
            return a[index] <= a[index - 1] && a[index] <= a[index + 1];
        }
    }

    static boolean isPeaksAndValleys(int[] a) {
        final int len = a.length;
        if (isPeak(a, 0)) {
            for (int i = 0; i < len; i += 2) {
                if (isPeak(a, i)) {
                    // good
                } else {
                    return false;
                }
            }
            for (int i = 1; i < len; i += 2) {
                if (isValley(a, i)) {
                    // good
                } else {
                    return false;
                }
            }
            return true;
        } else if (isValley(a, 0)) {
            for (int i = 0; i < len; i += 2) {
                if (isValley(a, i)) {
                    // good
                } else {
                    return false;
                }
            }
            for (int i = 1; i < len; i += 2) {
                if (isPeak(a, i)) {
                    // good
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    static int[] arrange(int[] a) {
        int len = a.length;
        int[] b = new int[len];
        Arrays.sort(a);
        int index = 0, lo = 0, hi = len;
        // odd length out of the way
        if (len % 2 != 0) {
            b[index++] = a[lo++];
        }
        // fill the rest
        while (index < len) {
            b[index++] = a[--hi];
            b[index++] = a[lo++];
        }
        return b;
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        int[] reorg = arrange(a);
        System.out.println(Arrays.toString(reorg));
        boolean check = isPeaksAndValleys(reorg);
        System.out.println("check = " + check);
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(100, 100);
        test(a);
    }
}
