package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/29/17
 * <p>
 * InterviewCake
 */
public class RotatedDictionary {

    /*
        find the index at which sorted segment begins that should have been starting the whole array
        "reverse binary search"
        values grow from start to split, fall to min, grow again
     */
    static int findStart(int[] a) {
        int i = 0, j = a.length - 1;

        // split is between i < j iff a[i] > a[j]
        // sample k in between i and j

        while (i < j) {
            final int k = i + (j - i) / 2;
            if (a[i] < a[k]) {  // k still in part A
                i = k;
            } else if (a[k] < a[j]) { // k in part B
                j = k;
            } else {
                return k;
            }
        }


        return 0;
    }

    private static int[] rotate(int[] a, int start) {
        final int[] b = new int[a.length];
        System.arraycopy(a, start, b, 0, a.length - start);
        System.arraycopy(a, 0, b, a.length - start, start);
        return b;
    }

    public static void main(String[] args) {
        int size = 20;
        int[] a = Utils.createRandomArray(size, size * 10);
        Arrays.sort(a);
        System.out.println("Arrays.toString(a) = " + Arrays.toString(a));
        int[] b = rotate(a, 9);
        System.out.println("Arrays.toString(b) = " + Arrays.toString(b));
        int found = findStart(b);
        System.out.println("found = " + found);
    }
}
