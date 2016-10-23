package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 10/18/16.
 */

public class Utils {
    static int[] createRandomArray(final int length, final int bound) {
        final int[] array = new int[length];
        final Random random = new Random(System.nanoTime());
        for (int i = length; --i >= 0; ) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }
}
