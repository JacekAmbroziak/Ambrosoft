package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/6/17.
 */
public class SecondLargestUdacity {
    /*
        Order statistics: max value & also 2nd and 3rd largest
        Can be done by sorting and taking the first k
        Or by a single linear scan and updating state
        Can also be generalized to INSERTING values to an array largest[k]
     */

    static int maxAnd2and3(int[] a) {
        int max = Integer.MIN_VALUE, max2 = max, max3 = max2;
        for (int i : a) {
            if (i > max) {
                max3 = max2;
                max2 = max;
                max = i;
            } else if (i > max2) {
                max3 = max2;
                max2 = i;
            } else if (i > max3) {
                max3 = i;
            }
        }

        System.out.println("max = " + max);
        System.out.println("max2 = " + max2);
        System.out.println("max3 = " + max3);
        return 0;
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(40, 100);
        System.out.println(Arrays.toString(a));
        maxAnd2and3(a);
    }
}
