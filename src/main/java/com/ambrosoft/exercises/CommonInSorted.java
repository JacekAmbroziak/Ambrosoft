package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/9/16.
 */
public class CommonInSorted {
    static int countCommon(int[] a, int[] b) {
        final int aLen = a.length;
        final int bLen = b.length;
        int common = 0;

        int i = 0, j = 0;
        while (i < aLen && j < bLen) {
            if (a[i] < b[j]) {
                ++i;
            } else if (a[i] > b[j]) {
                ++j;
            } else {
                ++common;
                ++i;
                ++j;
            }
        }
        return common;
    }

    public static void main(String[] args) {
        System.out.println(countCommon(new int[]{1, 2, 3, 4, 5, 6, 10}, new int[]{2, 5, 7, 10}));
    }
}
