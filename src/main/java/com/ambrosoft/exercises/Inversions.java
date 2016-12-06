package com.ambrosoft.exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jacek on 8/2/16.
 */
public class Inversions {
    public static void main(String[] args) throws IOException {
        System.out.println("count = " + countInversions(new int[]{6, 5, 4, 3, 2, 1}));
        System.out.println("count = " + countInversions(new int[]{1, 2, 3}));
        System.out.println("count = " + countInversions(new int[]{1, 2, 4, 3}));
        System.out.println("count = " + countInversions(new int[]{1, 3, 5, 2, 4, 6}));
        long start = System.nanoTime();
        System.out.println("count = " + countInversions(readIntegers("/Users/jacek/Downloads/Integers.txt")));
        long end = System.nanoTime();
        System.out.println("time = " + (end - start) / 1000);
    }

    static long countInversions(final int[] a) {
        int len = a.length;
        int[] aux = new int[len];
        return countInversionsAndSort2(a, aux, 0, len);
    }

    // input in a, sorted result back in a
    private static long countInversionsAndSort(final int[] a, final int[] aux, int lo, int hi) {
        final int len = hi - lo;
        if (len > 1) {
            final int mid = lo + len / 2;
            // count inversions in left & right halves
            final long lft = countInversionsAndSort(a, aux, lo, mid);
            final long rgt = countInversionsAndSort(a, aux, mid, hi);

            long splitInversions = 0;

            // merge into aux
            int k = lo, i = lo, j = mid;
            while (i < mid && j < hi) {
                if (a[i] < a[j]) {
                    aux[k++] = a[i++];
                } else {
                    aux[k++] = a[j++];
                    // all of the remaining elements of left array contribute inversions
                    // against the smaller element found in the right sorted array
                    splitInversions += mid - i; // the count of remaining elements from left array
                }
            }
            if (i == mid) {
                // possibly unnecessary copying back and forth
                System.arraycopy(a, j, aux, k, hi - j);
            }
            if (j == hi) {
                System.arraycopy(a, i, aux, k, mid - i);
            }
            // copy back merged aux to a
            System.arraycopy(aux, lo, a, lo, len);

            return lft + rgt + splitInversions;
        } else {
            return 0;
        }
    }

    private static long countInversionsAndSort2(final int[] a, final int[] aux, int lo, int hi) {
        final int len = hi - lo;

        switch (len) {
            case 0:
                throw new AssertionError();

            case 1:
                return 0;

            case 2:
                if (a[lo] > a[lo + 1]) {
                    final int temp = a[lo];
                    a[lo] = a[lo + 1];
                    a[lo + 1] = temp;
                    return 1;
                } else {
                    return 0;
                }

            default:
                final int mid = lo + len / 2;
                final long lft = countInversionsAndSort2(a, aux, lo, mid);
                final long rgt = countInversionsAndSort2(a, aux, mid, hi);
                // merge into aux
                long splitInversions = 0;
                for (int k = lo, i = lo, j = mid; ; ) {
                    if (a[i] < a[j]) {
                        aux[k++] = a[i];    // take from left
                        if (++i == mid) {   // left subarray exhausted
                            // just copy merged so far back to a
                            System.arraycopy(aux, lo, a, lo, k - lo);
                            break;
                        }
                    } else {
                        aux[k++] = a[j];    // take from right
                        splitInversions += mid - i; // the count of remaining elements from left array
                        if (++j == hi) {    // right exhausted
                            // copy remainder of left to last of merged
                            System.arraycopy(a, i, aux, k, mid - i);
                            // copy merged back to a
                            System.arraycopy(aux, lo, a, lo, len);
                            break;
                        }
                    }
                }
                return lft + rgt + splitInversions;
        }
    }

    static int[] readIntegers(String fileName) throws IOException {
        final ArrayList<Integer> ints = new ArrayList<>();
        {
            final BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = fileReader.readLine()) != null) {
                ints.add(Integer.parseInt(line));
            }
            fileReader.close();
        }
        final int size = ints.size();
        final int[] result = new int[size];
        for (int i = size; --i >= 0; ) {
            result[i] = ints.get(i);
        }
        return result;
    }
}
