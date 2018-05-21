package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/20/17
 * InterviewCake
 * <p>
 * Can greedy method work?
 * Biggest 3 elements if all positive but sorting nlogn
 * Finding 3 largest is doable w/o sorting but is not good enough when negative possible
 * Grow current best?
 * <p>
 * We use a greedy ↴ approach to solve the problem in one pass. At each iteration we keep track of:
 * <p>
 * highestProductOf3
 * highestProductOf2
 * highest
 * lowestProductOf2
 * lowest
 * When we reach the end, the highestProductOf3 is our answer. We maintain the others because they're necessary for keeping the highestProductOf3 up to date as we walk through the array. At each iteration, the highestProductOf3 is the highest of:
 * <p>
 * the current highestProductOf3
 * current * highestProductOf2
 * current * lowestProductOf2 (if current and lowestProductOf2 are both low negative numbers, this product is a high positive number).
 */
public class MaxProductOf3Elements {
    static int maxProduct(int[] a) {
        Arrays.sort(a, 0, 3);
        int x = a[0], y = a[1], z = a[2];
        int prod = x * y * z;
        boolean anyNeg = x < 0 || y < 0 || z < 0;

        for (int i = 3; i < a.length; ++i) {
            final int current = a[i];
            if (current < 0) {
                if (prod < 0 || anyNeg) { // replace biggest negative with current

                    anyNeg = true;
                }
            } else if (current > z) {
                prod = x * y * (z = current);
            } else if (current > y) {
                prod = x * (y = current) * z;
            } else if (current > x) {
                prod = (x = current) * y * z;
            }
        }

        return prod;
    }

    public static int highestProductOf3(int[] arrayOfInts) {    // Solution from InterviewCake
        if (arrayOfInts.length < 3) {
            throw new IllegalArgumentException("Less than 3 items!");
        }

        // we're going to start at the 3rd item (at index 2)
        // so pre-populate highests and lowest based on the first 2 items.
        // we could also start these as null and check below if they're set
        // but this is arguably cleaner
        int highest = Math.max(arrayOfInts[0], arrayOfInts[1]);
        int lowest = Math.min(arrayOfInts[0], arrayOfInts[1]);

        int highestProductOf2 = arrayOfInts[0] * arrayOfInts[1];
        int lowestProductOf2 = arrayOfInts[0] * arrayOfInts[1];

        // except this one--we pre-populate it for the first *3* items.
        // this means in our first pass it'll check against itself, which is fine.
        int highestProductOf3 = arrayOfInts[0] * arrayOfInts[1] * arrayOfInts[2];

        // walk through items, starting at index 2
        for (int i = 2; i < arrayOfInts.length; i++) {
            int current = arrayOfInts[i];

            // do we have a new highest product of 3?
            // it's either the current highest,
            // or the current times the highest product of two
            // or the current times the lowest product of two
            highestProductOf3 = Math.max(Math.max(
                    highestProductOf3,
                    current * highestProductOf2),
                    current * lowestProductOf2);

            // do we have a new highest product of two?
            highestProductOf2 = Math.max(Math.max(
                    highestProductOf2,
                    current * highest),
                    current * lowest);

            // do we have a new lowest product of two?
            lowestProductOf2 = Math.min(Math.min(
                    lowestProductOf2,
                    current * highest),
                    current * lowest);

            // do we have a new highest?
            highest = Math.max(highest, current);

            // do we have a new lowest?
            lowest = Math.min(lowest, current);
        }

        return highestProductOf3;
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println(highestProductOf3(a));
    }

    public static void main(String[] args) {
        test(new int[]{1, 2, 3, 4, 5});
        test(new int[]{1, 10, -5, 1, -100});
    }
}
