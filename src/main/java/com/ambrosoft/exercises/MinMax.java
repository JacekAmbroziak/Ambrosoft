package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/31/16.
 */

public class MinMax {

    // when BOTH min & max are sought, the trick is to test them in PAIRS, first between themselves, then with min,max

    static void findMinMax(int[] a) {
        final int len = a.length;

        int min, max, i, j;
        // initialize vars depending on a's length being even or odd
        if (len % 2 == 0) {
            if (a[0] < a[1]) {
                min = a[0];
                max = a[1];
            } else {
                min = a[1];
                max = a[0];
            }
            i = 2;
            j = 3;
        } else {
            min = max = a[0];
            i = 1;
            j = 2;
        }

        for (; j < len; i += 2, j += 2) {
            if (a[i] < a[j]) {
                if (a[i] < min) {
                    min = a[i];
                }
                if (a[j] > max) {
                    max = a[j];
                }
            } else {
                if (a[j] < min) {
                    min = a[j];
                }
                if (a[i] > max) {
                    max = a[i];
                }
            }
        }

        System.out.println("min = " + min);
        System.out.println("max = " + max);
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(51, 100);
        findMinMax(a);
        Arrays.sort(a);
        System.out.println("a[0] = " + a[0]);
        System.out.println("a[a.length-1] = " + a[a.length - 1]);
    }

}
