package com.ambrosoft.exercises;

import static com.ambrosoft.exercises.LocalMinimum.random1DArray;

/**
 * Created by jacek on 8/16/16.
 */
public class Partition {
    public static void main(String[] args) {
        int[] a = random1DArray(100);
        int split = partition(a);
        System.out.println("done " + split);
        check(a, split);
    }

    static int partition(final int[] a) {
        final int length = a.length;
        if (length > 1) {
            final int pivot = a[0];
            if (length == 2 && a[1] < pivot) {
                a[0] = a[1];
                a[1] = pivot;
                return 1;
            } else {
                int i = 1;
                // search for 1st element bigger than pivot
                while (i < length && a[i] <= pivot) {
                    ++i;
                }
                // i at first bigger, if any
                if (i < length) {
                    for (int k = i + 1; k < length; ++k) {
                        if (a[k] > pivot) {
                            // leave alone
                        } else {
                            final int temp = a[i];
                            a[i] = a[k];
                            a[k] = temp;
                            ++i;
                        }
                    }
                }
                return i;
            }
        }
        return -1;
    }

    static void check(int[] a, int split) {
        final int len = a.length;
        if (split > 0) {
            final int pivot = a[0];
            for (int i = split; --i >= 0; ) {
                assert a[i] <= pivot;
            }
            for (int i = len; --i >= split; ) {
                assert a[i] > pivot;
            }
        }
    }
}
