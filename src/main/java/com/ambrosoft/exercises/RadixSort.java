package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jacek on 10/14/16.
 */
public class RadixSort {
    static final int RADIX = 36;
    static final int CODELEN = 6;

    static char[] randomReservationCode(final Random random, final int charCount) {
        final char[] code = new char[charCount];
        for (int i = charCount; --i >= 0; ) {
//            code[i] = Character.toUpperCase(Character.forDigit(random.nextInt(RADIX), RADIX));
            code[i] = toCode(random.nextInt(RADIX));
        }
        return code;
    }

    static char[][] randomCodes(final int length) {
        final char[][] codes = new char[length][];
        final Random random = new Random(System.nanoTime());
        for (int i = codes.length; --i >= 0; ) {
            codes[i] = randomReservationCode(random, CODELEN);
        }
        return codes;
    }

    static int[] countValues(int[] array, int bound) {
        final int[] counters = new int[bound];
        for (int i = array.length; --i >= 0; ) {
            ++counters[array[i]];
        }
        // cumulative counts will provide 'to' indices for placement of values
        for (int i = 1; i < bound; ++i) {
            counters[i] += counters[i - 1];
        }
        return counters;
    }

    private static int codeValue(final char code) {
        return code < 'A' ? code - '0' : code - 'A' + 10;
    }

    private static char toCode(final int value) {
        return (char) (value < 10 ? value + '0' : value - 10 + 'A');
    }

    static char[][] sortCodes(char[][] codes) {
        final int[] counters = new int[RADIX];
        final int length = codes.length;
        char[][] sorted = new char[length][];
        // sort by columns starting from the left
        for (int col = CODELEN - 1; ; ) {
            // count values in column
            for (int row = length; --row >= 0; ) {
                ++counters[codeValue(codes[row][col])];
            }
            // cumulative counts will provide 'to' indices for placement of values
            for (int i = 1; i < RADIX; ++i) {
                counters[i] += counters[i - 1];
            }
            // walk down the original array to preserve stability
            for (int row = length; --row >= 0; ) {
                final char[] code = codes[row];
                sorted[--counters[codeValue(code[col])]] = code;
            }
            // reset counters
            if (col > 0) {
                Arrays.fill(counters, 0);
                // swap source and target
                final char[][] temp = codes;
                codes = sorted;
                sorted = temp;
                --col;
            } else {
                return sorted;
            }
        }
    }

    public static void main(String[] args) {
        char[][] codes = randomCodes(20);
        for (char[] chars : codes) {
            System.out.println(Arrays.toString(chars));
        }

        codes = sortCodes(codes);
        System.out.println("-----------------");
        for (char[] chars : codes) {
            System.out.println(Arrays.toString(chars));
        }
    }
}
