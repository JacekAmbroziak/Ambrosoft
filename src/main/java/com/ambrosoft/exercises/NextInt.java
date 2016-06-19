package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 6/18/16.
 */
public class NextInt {
    private static final Random rnd = new Random();


    // bad next int
    static int nextInt(final int n) {
        return Math.abs(rnd.nextInt()) % n;
    }

    public static void main(String[] args) {
        final int n = (Integer.MAX_VALUE / 3) * 2;

        // skewed result using naive bounded nextInt
        int low = 0;
        for (int i = 0; i < 1000000; i++) {
            if (nextInt(n) < n / 2) {
                ++low;
            }
        }
        System.out.println("low = " + low);

        //
        low = 0;
        for (int i = 0; i < 1000000; i++) {
            if (rnd.nextInt(n) < n / 2) {
                ++low;
            }
        }
        System.out.println("low = " + low);
    }
}
