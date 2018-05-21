package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/24/17
 */
public class BinarySearch {

    static int binSearch(int[] a, int element) {
        int i = 0, j = a.length - 1;

        while (i <= j) {
            final int k = i + (j - i) / 2;
            if (element < a[k]) {
                j = k - 1;
            } else if (element > a[k]) {
                i = k + 1;
            } else {
                return k;
            }
        }
        return -1;
    }

    static void test(int len) {
        int[] a = Utils.createRandomArray(20,200);
        int element = a[0];
//        int element = 13;
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        int k = binSearch(a, element);
        System.out.println("element = " + element);
        System.out.println("k = " + k);
    }

    public static void main(String[] args) {
        test(50);
    }
}
