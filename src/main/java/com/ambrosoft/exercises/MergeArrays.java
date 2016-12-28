package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/27/16.
 */

public class MergeArrays {
    /*
        Merge sorted 2nd array into empty space in sorted 1st array in place

        Feels rather trivial if populating the empty space from the right end
        but stopping condition & invariant need to be figured out
        Actually, the problem is interesting in that when elements of a are moved to end, buffer area grows
     */


    private static void merge(int[] a, int[] b) {
        int index = a.length - 1;   // where data will be placed
        int bi = b.length - 1;  // last el of b
        int ai = a.length - b.length - 1;   // last original element of a

        while (ai >= 0 && bi >= 0) {
            if (a[ai] <= b[bi]) {
                a[index--] = b[bi--];
            } else {
                a[index--] = a[ai--];
            }
        }
        while (bi >= 0) {
            a[index--] = b[bi--];
        }
    }

    public static void main(String[] args) {
        for (int times = 100000; --times >= 0; ) {
            int[] a = Utils.createRandomArray(200);
            int[] b = Utils.createRandomArray(200);
            // this array created for testing
            int[] c = Arrays.copyOf(a, a.length + b.length);
            System.arraycopy(b, 0, c, a.length, b.length);
            Arrays.sort(c);

            Arrays.sort(a);
            Arrays.sort(b);
            final int[] a1 = Arrays.copyOf(a, a.length + b.length);
            merge(a1, b);
            if (!Arrays.equals(a1, c)) {
                throw new Error();
            }
        }
    }
}
