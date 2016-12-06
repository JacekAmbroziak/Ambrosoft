package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/10/16.
 */
public class Sorts {


    static void selection(int[] a) {
        final int last = a.length - 1;
        for (int i = 0; i < last; ++i) {
            // find smallest from i to last
            int indexOfSmallest = i;
            for (int j = i; j < a.length; ++j) {
                if (a[j] < a[indexOfSmallest]) {
                    indexOfSmallest = j;
                }
            }

            int temp = a[i];
            a[i] = a[indexOfSmallest];
            a[indexOfSmallest] = temp;
        }
    }

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            final int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];    // shift values to the right
                --j;
            }
            a[j + 1] = key;
        }
    }

    static int[] generateRandomArray(final int size) {
        final int[] result = new int[size];
        for (int i = result.length; --i >= 0; ) {
            result[i] = i;
        }
        Utils.shuffle(result);
        return result;
    }

    public static void main(String[] args) {
        int[] example = generateRandomArray(40);
        System.out.println(Arrays.toString(example));
        insertion(example);
        System.out.println(Arrays.toString(example));
    }
}
