package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/7/16.
 * <p>
 * Volume of water a bar histogram can hold.
 * Bar width is 1
 */
public class VolumeOfHistogram {
    /*
        bars themselves displace water
        water can be contained between positive bars
        approach:
        count water "bars"
        tricky with D&C or DP -- I don't see subproblems
     */

    static int volume(int[] hist) {
        return 0;
    }

    static void test(int[] hist) {
        System.out.println(Arrays.toString(hist));
        System.out.println("volume(hist) = " + volume(hist));
    }

    public static void main(String[] args) {
        int[] hist = {0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0};
        test(hist);
    }
}
