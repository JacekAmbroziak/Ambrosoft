package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacek on 12/15/16.
 */
public class SumSwap {

    static void sumSwap(int[] a, int[] b) {
        final int sumA = sum(a);
        final int sumB = sum(b);
        System.out.println("sumA = " + sumA);
        System.out.println("sumB = " + sumB);
        final int diff = sumA - sumB;
        if (diff < 0) {
            sumSwap(b, a, -diff);
        } else if (diff > 0) {
            sumSwap(a, b, diff);
        } else {
            System.out.println("diff = " + diff);
        }
    }

    // a has bigger sum than b by positive diff
    static void sumSwap(int[] a, int[] b, int diff) {
        System.out.println("a = " + Arrays.toString(a));
        System.out.println("b = " + Arrays.toString(b));
        System.out.println("diff = " + diff);

        // we need to swap bigger element from a for smaller element from b
        // diff has to be even

        if (diff % 2 != 0) {
            System.out.println("can't do it! Diff odd");
        } else {
            final int elDiff = diff / 2;
            // find a pair of elements differing by elDiff
            final Map<Integer, Integer> pairs = new HashMap<>();

            for (int i : a) {
                pairs.put(i - elDiff, i);
            }

            for (int i : b) {
                final Integer fromA = pairs.get(i);
                if (fromA != null) {
                    System.out.println("from A = " + fromA);
                    System.out.println("from B = " + i);
                }
            }
        }
    }

    private static int sum(int[] a) {
        int sum = 0;
        for (int i : a) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] a = {4, 1, 2, 1, 1, 2};
        int[] b = {3, 6, 3, 3};
        sumSwap(a, b);
    }
}
