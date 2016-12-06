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
        maybe scan left to right, O(n)
            when a bar is seen, we don't yet know what's in the future
        we can learn something from computing deltas first
        borders cannot hold water
        some histograms (convex) will not hold water
        each water area has constant height/level, the min height of its 2 defining bars
        maybe 1 scan to figure out that height, 2 scan to compute volume?
            really looking for concave areas...
        not sure if its helpful to imagine water level falling from max to 0, and checking where it can remain?
        looking at drawing, it appears that sometimes a water area can be 'closed' on some prefixes
            eg. the 1st concave area between 4 & 6 cannot hold more water in the future
            are between 6 & 5 is tricky
        water level will not be higher than max bar
            we could assume max water bar height everywhere (subtracting bars themselves), then remove water that cannot stay
        actually, D&C does not look so bad :-)
            split subproblems at maximums!
            water (if any) will be to the left and right of a maximum
        trimming zeros might be a good idea

        ***

         Final analysis:
         1) D&C is possible: look for splitting into additive parts, look for base cases allowing direct comp
         2) but need to be flexible about how problem can be split (maxima)
         3) also flexible about intervals (i,j at bars)
         4) initial call (trimming zeros) does additional work before 1st recursive call

     */

    // unfinished solution; not sure how deltas can help
    static int volume(final int[] hist) {
        final int len = hist.length;
        final int[] deltas = new int[len];
        deltas[0] = hist[0];  // as if delta from 0
        for (int i = 1; i < len; i++) {
            deltas[i] = hist[i] - hist[i - 1];
        }
        System.out.println(Arrays.toString(deltas));
        return 0;
    }

    // find max value in (i, j)
    private static int findIndexOfMaxInside(final int[] hist, int i, final int j) {
        int index = -1;
        for (int max = Integer.MIN_VALUE; ++i < j; ) {
            if (hist[i] > max) {
                max = hist[index = i];
            }
        }
        return index;
    }

    // real D&C work
    // first & last are positive bars delimiting an area that can possibly contain water
    private static int divideAndConquer(int[] hist, int first, int last) {
        if (last - first < 2) { // single bar or 2 bars don't hold water
            return 0;
        } else {
            final int idxMax = findIndexOfMaxInside(hist, first, last);
            final int maxVal = hist[idxMax];
            final int level = Math.min(hist[first], hist[last]);

            if (maxVal <= level) {    // hollow area: base case!
                int water = 0;
                for (int i = first + 1; i < last; ++i) {
                    water += level - hist[i];   // subtract 'bottom'
                }
                return water;
            } else {    // maxVal sticks above water: conquer
                return divideAndConquer(hist, first, idxMax) + divideAndConquer(hist, idxMax, last);
            }
        }
    }

    static int divideAndConquer(final int[] hist) {
        final int len = hist.length;
        // trim zeros from either last to first recursion with canonical form of the histogram w/ positive bars
        int start = 0;
        while (start < len && hist[start] == 0) {
            ++start;
        }
        if (start < len) {
            // first positive element at (valid index) first
            int end = len;
            while (hist[--end] == 0) {
            }
            // now also last is at positive element
            return divideAndConquer(hist, start, end);
        } else {
            return 0;   // all zeros
        }
    }

    // from GeeksForGeeks
    // linear, using additional space for cumulative maxima that allow to look up max on left and right
    static int linear(int[] hist) {
        final int len = hist.length;
        // look from the point of view of each individual x (rather than in terms of areas)
        // each individual position can contribute a bar of water from the top of the bar to water level
        // ... and water level, if any, is determined by the min of 2 higher bars to left and right

        // prepare maxima from the left
        final int[] lftMax = new int[len];
        for (int i = 0, max = 0; i < len; i++) {
            max = lftMax[i] = Math.max(max, hist[i]);
        }

        // prepare maxima from the right
        final int[] rgtMax = new int[len];
        for (int i = len, max = 0; --i >= 0; ) {
            max = rgtMax[i] = Math.max(max, hist[i]);
        }

        int water = 0;
        for (int i = 0; i < len; i++) {
            final int level = Math.min(lftMax[i], rgtMax[i]);
            final int waterAbove = level - hist[i]; // negative if bar higher than level
            if (waterAbove > 0) {
                water += waterAbove;
            }
        }
        return water;
    }

    static void test(int[] hist) {
        System.out.println(Arrays.toString(hist));
        System.out.println("linear(hist) = " + linear(hist));
        System.out.println("divideAndConquer(hist) = " + divideAndConquer(hist));
    }

    public static void main(String[] args) {
        test(new int[]{0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 5, 0, 1, 0, 0, 0});
        test(new int[]{1, 2, 3});
        test(new int[]{1, 2, 3, 2, 1});
        test(new int[]{1, 2, 3, 0, 1});
    }
}
