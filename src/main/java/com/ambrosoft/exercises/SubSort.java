package com.ambrosoft.exercises;

/**
 * Created by jacek on 4/12/16.
 */

public class SubSort {


    public static void main(String[] args) {
        subSort(new int[]{1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19});
//        subSort(new int[]{1, 1, 2, 2, 3, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19});
        subSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        subSort(new int[]{1, 1, 1, 1, 1, 1, 1});
        subSort(new int[]{1, 1, 1, 3, 1, 1, 1});
        subSort(new int[]{1, 2, 3, 4, 1, 2, 3});
    }

    // find smallest interval that has to be sorted so whole array is sorted

    static void subSort(final int[] a) {
        int i = 0;
        int j = a.length - 1;

        if (a[i] > a[j]) {
            System.out.println("whole array needs to be sorted");
            return;
        }

        // find sorted prefix
        for (; i < j && a[i] <= a[i + 1]; ++i) {
        }

        if (i == j) {
            System.out.println("already sorted");
            return;
        }

        // find sorted suffix
        for (; i < j && a[j - 1] <= a[j]; --j) {
        }

        // find smallest & biggest values in the middle part

        int min = a[j];
        int max = a[i];

        for (int k = i + 1; k < j; ++k) {
            final int val = a[k];
            if (val < min) {
                min = val;
            } else if (val > max) {
                max = val;
            }
        }

        // on the left find where smallest belongs
        for (; i >= 0 && a[i] > min; --i) {
        }
        ++i;

        // on the right find where biggest belongs
        for (; j < a.length && a[j] < max; ++j) {
        }
        --j;

        System.out.println("i = " + i);
        System.out.println("j = " + j);
    }
}
