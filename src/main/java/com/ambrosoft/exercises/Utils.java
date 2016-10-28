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

    static int[] createRandomArray(final int length) {
        final int[] array = new int[length];
        final Random random = new Random(System.nanoTime());
        for (int i = length; --i >= 0; ) {
            array[i] = random.nextInt();
        }
        return array;
    }

    static void shuffle(final int[] array) {
        final Random rand = new Random(System.nanoTime());
        for (int i = array.length; --i > 0; ) {
            final int index = rand.nextInt(i + 1);  // i+1 exclusive, so i inclusive
            // swap last in interval 0..i with randomly selected one
            final int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
