package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/5/16.
 */
public class TwoSum {

    // in a sorted array look for 2 values with given sum
    static void twoSum(int[] sorted, final int val) {
        int i = 0, j = sorted.length - 1;
        while (i < j) {
            final int sum = sorted[i] + sorted[j];
            if (sum < val) {
                ++i;
            } else if (sum > val) {
                --j;
            } else {
                System.out.println("n1 = " + sorted[i]);
                System.out.println("n2 = " + sorted[j]);
                return;
            }
        }
        System.out.println("not found");
    }

    static void test(int[] a, int sum) {
        System.out.println(Arrays.toString(a));
        twoSum(a, sum);
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(50);
        Utils.limitArray(a, 30);
        Arrays.sort(a);
        test(a, 20);
    }
}
