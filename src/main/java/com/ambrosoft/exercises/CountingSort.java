package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/14/16.
 */

public class CountingSort {

    static int[] countValues(int[] array, int bound) {
        final int[] counters = new int[bound];
        for (int i = array.length; --i >= 0; ) {
            ++counters[array[i]];
        }
        // cumulative counts will provide 'to' indices for placement of values
        for (int i = 1; i < bound; ++i) {
            counters[i] += counters[i - 1];
        }
        return counters;
    }

    public static void main(String[] args) {
        final int bound = 5;
        final int length = 20;

        final int[] array = Utils.createRandomArray(length, bound);
        System.out.println(Arrays.toString(array));

        final int[] counters = countValues(array, bound);

        System.out.println(Arrays.toString(counters));

        final int[] sorted = new int[array.length];
        // walk down the original array to preserve stability
        for (int i = array.length; --i >= 0; ) {
            final int val = array[i];
            sorted[--counters[val]] = val;
        }

        System.out.println(Arrays.toString(sorted));
        System.out.println(Arrays.toString(counters));
    }
}
