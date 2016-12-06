package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/5/16.
 */
public class SubarrayGivenSum {


    // grow a sum from 'first' until last of array, or found, or too big
    // when too big, advance first...
    static void find(int[] a, final int val) {
        System.out.println("\nFIND");
        final int len = a.length;
        int start = 0, sum = 0;
        for (int i = start; i < len; ++i) {
            sum += a[i];
            if (sum == val) {
                System.out.println(String.format("first=%d, last=%d", start, i + 1));
                return;
            } else if (sum > val) {
                sum -= a[i--];
                do {
                    sum -= a[start++];
                } while (sum > val);
            }
        }
        System.out.println("not found");
    }

    static void bruteForce(int[] a, int val) {
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    if ((sum += a[k]) >= val) {
                        break;
                    }
                }
                if (sum == val) {
                    System.out.println(String.format("i=%d, j=%d", i, j));
                    for (int k = i; k <= j; k++) {
                        System.out.println("a[k] = " + a[k]);
                    }
                }
            }
        }
    }

    static void test(int[] a, int val) {
        System.out.println(Arrays.toString(a));
        System.out.println("val = " + val);
        bruteForce(a, val);
        find(a, val);
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(20, 30);
//        int[] a = {25, 19, 17, 13};
        test(a, 30);
    }
}
