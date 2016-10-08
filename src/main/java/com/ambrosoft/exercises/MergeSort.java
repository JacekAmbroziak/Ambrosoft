package com.ambrosoft.exercises;

/**
 * Created by jacek on 8/2/16.
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] a = {7, 6, 3, 4, 5, 2, 1};
        mergeSort(a);
    }

    static void mergeSort(final int[] a) {
        int len = a.length;
        mergeSort(a, new int[len], 0, len);
        System.out.println("done");
    }

    // input in a, sorted result back in a
    // we are only considering an interval of a [lo, hi)
    private static void mergeSort(final int[] a, final int[] aux, int lo, int hi) {
        final int len = hi - lo;
        switch (len) {
            case 0:
                throw new AssertionError();

            case 1:
                break;

            case 2:
                if (a[lo] > a[lo + 1]) {
                    final int temp = a[lo];
                    a[lo] = a[lo + 1];
                    a[lo + 1] = temp;
                }
                break;

            default:
                final int mid = lo + len / 2;
                mergeSort(a, aux, lo, mid);
                mergeSort(a, aux, mid, hi);
                // merge into aux
                for (int k = lo, i = lo, j = mid; ; ) {
                    if (a[i] < a[j]) {
                        aux[k++] = a[i];    // take from left
                        if (++i == mid) {   // left subarray exhausted
                            // just copy merged so far back to a
                            System.arraycopy(aux, lo, a, lo, k - lo);
                            return;
                        }
                    } else {
                        aux[k++] = a[j];    // take from right
                        if (++j == hi) {    // right exhausted
                            // copy remainder of left to end of merged
                            System.arraycopy(a, i, aux, k, mid - i);
                            // copy merged back to a
                            System.arraycopy(aux, lo, a, lo, len);
                            return;
                        }
                    }
                }
        }
    }
}
