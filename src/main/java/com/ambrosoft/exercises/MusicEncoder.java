package com.ambrosoft.exercises;

/*
    https://www.codewars.com/kata/a-simple-music-encoder

    looking for
    1) repeated values,
    2) growing/falling series

    A sequence of 2 or more identical numbers is shortened as number*count
    A sequence of 3 or more consecutive numbers is shortened as first-last. This is true for both ascending and descending order
    A sequence of 3 or more numbers with the same interval is shortened as first-last/interval. Note that the interval does NOT need a sign
    Compression happens left to right

    Looking at differences
    2 or more identical numbers are spotted when 1 or more 0s spotted
    Other cases spotted when a diff repeats 2 or more times

    We need to remember up to where data has been compressed so we don't forget values not in groups
    Special cases: no numbers, 1 number, 2?

 */

import java.util.Arrays;

public class MusicEncoder {

    private static void findSegments(int[] a) {
        final int len = a.length;
        if (len > 1) {
            for (int start = 0, current = 1, val = a[start]; ; ++current) {
                while (current < len && a[current] == val) {    // try to extend
                    ++current;
                }
                if (current < len) {    // val changed
                    System.out.println(String.format("same val %d from %d to %d", val, start, current));
                    val = a[start = current];
                } else {    // reached the end
                    System.out.println(String.format("same val %d from %d to %d", val, start, current));
                    System.out.println("done");
                    break;
                }
            }
        }
    }


    /**
     * @param a               original values to be compressed
     * @param offset          index of first element in a not yet output
     * @param deltaGroupStart index of first delta in a candidate group
     * @param deltaGroupEnd   index beyond the last delta equal to deltas[groupStart]
     * @param delta           value of the delta in the group
     * @param sb              StringBuilder accumulating output
     * @return new offset: start of array segment still to be processed
     */
    private static int outputGroup(int[] a, int offset, int deltaGroupStart, int deltaGroupEnd, int delta, StringBuilder sb) {
        final int deltaGroupLength = deltaGroupEnd - deltaGroupStart;
        if (delta == 0) {   // repeated values
            sb.append(a[offset]).append('*').append(deltaGroupLength + 1);
            offset += 1;
        } else if (deltaGroupLength > 1) {  // range
            sb.append(a[offset]).append('-').append(a[offset + deltaGroupLength]);
            if (delta < -1) {
                sb.append('/').append(-delta);
            } else if (delta > 1) {
                sb.append('/').append(delta);
            }
            offset += 1;
        } else {
            sb.append(a[offset]);
        }
        return offset + deltaGroupLength;
    }

    static String encode(final int[] a) {
        if (a == null || a.length == 0) {
            return "";
        } else if (a.length == 1) {
            return Integer.toString(a[0]);
        } else {
            final int length = a.length;
            final int[] deltas = new int[length];   // it is also possible to compute deltas on the fly
            for (int i = 1; i < length; i++) {
                deltas[i] = a[i] - a[i - 1];
            }

            final StringBuilder sb = new StringBuilder();
            for (int offset = 0; ; ) {   // subproblem: array suffix starting at offset
                final int start = offset + 1;   // beginning of a group of deltas
                if (start == length) {  // single value remaining
                    return sb.append(a[offset]).toString();
                } else {
                    final int delta = deltas[start];
                    int end = start + 1;
                    while (end < length && deltas[end] == delta) {    // try to extend group of identical deltas
                        ++end;
                    }
                    if ((offset = outputGroup(a, offset, start, end, delta, sb)) < length) {
                        sb.append(',');
                    } else {
                        break;
                    }
                }
            }
            return sb.toString();
        }
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println(encode(a));
    }

    public static void main(String[] args) {
        test(new int[]{6, 4, 2, 5, 5, 6});
        test(new int[]{1, 1, 2, 3, 4, 5, 7, 9});
        test(new int[]{2, 3, 4, 5, 7, 9});
        test(new int[]{7, 9});
        test(new int[]{1, 1, 1, 2, 3, 4, 7, 9, 11, 11, 9, 7, 5, 1});
        test(new int[]{1, 2, 2, 3});
        test(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 2});
        test(new int[]{1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20});
    }
}
