package com.ambrosoft.exercises;

import java.util.LinkedHashSet;
import java.util.Random;

/**
 * Created by jacek on 10/18/16.
 */

public class Utils {
    static int[] createRandomArray(final int length, final int bound) {
        final int[] array = new int[length];
        final Random random = new Random(System.currentTimeMillis());
        for (int i = length; --i >= 0; ) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }

    static int[] createRandomArray(final int length) {
        final int[] array = new int[length];
        final Random random = new Random(System.currentTimeMillis());
        for (int i = length; --i >= 0; ) {
            array[i] = random.nextInt();
        }
        return array;
    }

    static int[] createRandomArrayNoDups(final int length, final int limit) {
        final Random random = new Random(System.currentTimeMillis());
        final LinkedHashSet<Integer> set = new LinkedHashSet<>();
        do {
            set.add(random.nextInt() % limit);
        } while (set.size() < length);

        final int[] array = new int[length];
        int i = 0;
        for (Integer integer : set) {
            array[i++] = integer;
        }
        return array;
    }

    static void shuffle(final int[] array) {
        final Random rand = new Random(System.currentTimeMillis());
        for (int i = array.length; --i > 0; ) {
            final int index = rand.nextInt(i + 1);  // i+1 exclusive, so i inclusive
            // swap last in interval 0..i with randomly selected one
            final int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    static void limitArray(int[] a, int limit) {
        for (int i = a.length; --i >= 0; ) {
            a[i] %= limit;
        }
    }

    static String randomString(final int length) {
        final Random random = new Random();
        final char[] letters = new char[length];
        for (int i = length; --i >= 0; ) {
            letters[i] = (char) ('A' + random.nextInt(26));
        }
        return new String(letters);
    }

    static String reverseString(final String input) {
        if (input.length() < 2) {
            return input;
        } else {
            final char[] chars = input.toCharArray();
            for (int i = 0, j = chars.length - 1; i < j; ) {
                final char temp = chars[i];
                chars[i++] = chars[j];
                chars[j--] = temp;
            }
            return new String(chars);
        }
    }

    static int[] reverseArray(int[] a) {
        final int len = a.length;
        final int[] b = new int[len];
        for (int i = len, j = 0; --i >= 0; ) {
            b[j++] = a[i];
        }
        return b;
    }
}
