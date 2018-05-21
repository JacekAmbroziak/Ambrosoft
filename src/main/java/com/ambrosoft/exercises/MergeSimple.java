package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 1/2/18
 */
class MergeSimple {

    static int[] merge(int[] a, int[] b) {
        final int aLen = a.length;
        final int bLen = b.length;
        final int[] r = new int[aLen + bLen];
        int ai = 0, bi = 0, ri = 0;

        while (ai < aLen && bi < bLen) {
            r[ri++] = a[ai] < b[bi] ? a[ai++] : b[bi++];
        }
        while (ai < aLen) {
            r[ri++] = a[ai++];
        }
        while (bi < bLen) {
            r[ri++] = b[bi++];
        }
        return r;
    }

    /*
        faster version
        only checking for array exhaustion when it can happen, switching to copying other array and returning
     */
    static int[] merge2(final int[] a, final int[] b) {
        final int aLen = a.length;
        final int bLen = b.length;
        final int[] merged = new int[aLen + bLen];
        for (int ai = 0, bi = 0, ri = 0; ; ) {
            if (a[ai] < b[bi]) {
                merged[ri++] = a[ai];
                if (++ai == aLen) { // a can be exhausted only here
                    while (bi < bLen) {
                        merged[ri++] = b[bi++];
                    }
                    return merged;
                }
            } else {
                merged[ri++] = b[bi];
                if (++bi == bLen) { // ditto for b (last b has just been used)
                    while (ai < aLen) {
                        merged[ri++] = a[ai++];
                    }
                    return merged;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(100, 100);
        int[] b = Utils.createRandomArray(200, 100);
        Arrays.sort(a);
        Arrays.sort(b);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(merge(a, b)));
        System.out.println(Arrays.toString(merge2(a, b)));
    }
}
