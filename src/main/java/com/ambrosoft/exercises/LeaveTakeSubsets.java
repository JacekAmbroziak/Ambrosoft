package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/20/16.
 * <p>
 * from Jackson Gabbard party problem
 */
public class LeaveTakeSubsets {
    /*
        all subsets: recurse with current left + recurse w/ taken
        when array all 1, this (of course) enumerates binary numbers
        which gives us another way of doing selection: just iterate and select when '1' at position
     */

    static void recurse(final int[] a, final int length, final int current) {
        if (current == length) {
            System.out.println(Arrays.toString(a));
        } else {
            final int[] leave = Arrays.copyOf(a, length);
            leave[current] = 0; // 0 to stand for not present
            recurse(leave, length, current + 1);
            recurse(a, length, current + 1);
        }
    }

    static void subsets(int[] a) {
        recurse(a, a.length, 0);
    }

    public static void main(String[] args) {
        subsets(new int[]{1, 2, 3, 4});
        subsets(new int[]{1, 1, 1, 1});
    }
}
