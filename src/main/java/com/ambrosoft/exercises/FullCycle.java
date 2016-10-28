package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 10/27/16.
 */
public class FullCycle {

    // think about border cases: empty array, array of one element

    // approach: sum of all indexes is known ahead of time
    // compute the sum of indexes encountered, after length revolutions we should arrive at precomputed
    // correctness proof?
    // if we have full cycle, all indexes will be encountered and will sum to expected value
    // can we imagine reaching expected value in length revolutions w/o seeing all indices?
    // we'd need a loop of index maxSum/(length-1): just looping well chosen index n times will reach max sum
    // we need to guard against deltas of 0 !!!
    // deltas cannot be 0 in any case as they directly signify loops

    // as it is, the code below is buggy: a loop in the middle will also reach maxSum in n reps
    static boolean isFullCycle(int[] a) {
        final int len = a.length;
        int sum = 0;
        final int maxSum = len * (len - 1) / 2;

        int index = 0;
        for (int i = 0; i < len; i++) {
            sum += index;
            index = (index + a[index]) % len;
            if (index < 0) {
                index += len;
            }
        }
        return sum == maxSum;
    }

    // GOOG solution better
    // ex definitione: full cycle means reaching beginning after length cycles, no more, no less
    // no additional memory, O(n), very simple
    static boolean isFullCycle2(int[] a) {
        final int len = a.length;
        int index = 0;
        for (int i = 0; i < len; i++) {
            index = (index + a[index]) % len;
            if (index < 0) {
                index += len;
            }
            if (index == 0 && i < len - 1) {
                return false;
            }
        }
        return index == 0;
    }

    static boolean isFullCycle3(int[] a, int start) {
        final int len = a.length;
        int index = start;
        for (int i = 0; i < len; i++) {
            index = (index + a[index]) % len;
            if (index < 0) {
                index += len;
            }
            if (index == start && i < len - 1) {
                return false;
            }
        }
        return index == start;
    }

    static void shufflePrefix(final int[] array, final int limit) {
        final Random rand = new Random(System.nanoTime());
        for (int i = limit; --i > 0; ) {
            final int index = rand.nextInt(i + 1);  // i+1 exclusive, so i inclusive
            // swap last in interval 0..i with randomly selected one
            final int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    static int[] generateFullCycle(final int len) {
        // generate random order of nodes to visit, except that we need 0 at the end to complete loop
        // the other indices need to be shuffled
        final int[] visitOrder = new int[len];
        // resisting temptation to micro-optimize
        for (int i = 0; i < len - 1; i++) {
            visitOrder[i] = i + 1;
        }
        // zero stays in last position
        shufflePrefix(visitOrder, len - 1);

        final int[] deltas = new int[len];
        for (int i = 0, current = 0; i < len; ++i) {
            final int next = visitOrder[i];
            deltas[current] = next - current;    // store delta
            current = next;
        }
        return deltas;
    }

    public static void main(String[] args) {
        int[] array = Utils.createRandomArray(100);
        for (int i = 0; i < array.length; i++) {
            array[i] %= 100;
        }
//        System.out.println(Arrays.toString(array));
//        System.out.println(-111 % 20);
//        System.out.println(111 % 20);
        System.out.println("isFullCycle(array) = " + isFullCycle(array));
        int[] a = generateFullCycle(100000);
        System.out.println("isFullCycle(array) = " + isFullCycle(a));
        System.out.println("isFullCycle2(array) = " + isFullCycle2(a));
        System.out.println("isFullCycle3(array) = " + isFullCycle3(a, 0));
        System.out.println("isFullCycle3(array) = " + isFullCycle3(a, 120));
        System.out.println("isFullCycle3(array) = " + isFullCycle3(a, 11));
        System.out.println("isFullCycle3(array) = " + isFullCycle3(a, 117));

    }
}
