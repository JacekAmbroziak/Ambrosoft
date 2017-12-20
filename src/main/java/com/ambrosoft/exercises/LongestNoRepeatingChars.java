package com.ambrosoft.exercises;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

/**
 * Created by jacek on 11/4/16.
 */

public class LongestNoRepeatingChars {

    /*

        d&c: longest in left half, or right half or crossing: nCols log nCols

        possibly DP

        longest in prefix a[0..i)
            a[i] makes it longer or not

        how to detect repeats?

        - can scan array and increase char counters, store counter value at aux[i]
        - can store 'next' position of a given char in aux[i], looking for longest stretch of zeros

        At most 26 length

     */

    static String lwor(String input) {
        char[] chars = input.toCharArray();
        int result = lwor(chars, 0, chars.length);
        System.out.println("result = " + result);
        return "";
    }

    private static int lwor(char[] chars, int start, int end) {
        final int length = end - start;
        if (length < 1) {
            throw new Error();
        }
        if (length == 1) {
            return 1;
        } else if (length == 2) {
            return chars[start] != chars[start + 1] ? 2 : 1;
        } else {
            final int mid = start + length / 2;

            final int lft = lwor(chars, start, mid);
            final int rgt = lwor(chars, mid, end);
            final int ctr = crossing(chars, start, mid, end);

            if (lft >= rgt && lft >= ctr) {
                return lft;
            }
            if (rgt >= lft && rgt >= ctr) {
                return rgt;
            }
            return ctr;
        }
    }

    private static boolean canExtend(char[] chars, int start, int end, char c) {
        while (start < end) {
            if (chars[start++] == c) {
                return false;
            }
        }
        return true;
    }

    private static int crossing(char[] chars, int start, int mid, int end) {
        int i = mid, j = mid;
        while (i > start && j + 1 < end) {
            if (canExtend(chars, i, j, chars[i - 1])) {
                --i;
            } else {
                while (j < end - 1 && canExtend(chars, i, j, chars[j + 1])) {
                    ++j;
                }
                return j - i;
            }
            if (canExtend(chars, i, j, chars[j + 1])) {
                ++j;
            } else {
                while (i > 0 && canExtend(chars, i, j, chars[i - 1])) {
                    --i;
                }
                return j - i;
            }
        }
        return 0;
    }

    static void linear(final char[] a) {
        final int len = a.length;
        int maxstart = 0, maxend = 1;
        for (int i = 1, start = 0; i < len; ++i) {
            final char c = a[i];    // current char
            // check back for dups of current char
            int j = i - 1;
            while (j >= start && a[j] != c) {
                --j;
            }
            if (j < start) {  // all chars different
                if (i - j > maxend - maxstart) {
                    maxstart = start;
                    maxend = i + 1;
                }
            } else {    // a[j] == c, so duplicate at j
                start = j + 1;
            }
        }

        System.out.println("maxstart = " + maxstart);
        System.out.println("maxend = " + maxend);
        System.out.println("(Lin) len = " + (maxend - maxstart));
        System.out.println(String.copyValueOf(a, maxstart, maxend - maxstart));
    }

    static void linearWithSet(final char[] a) {
        final int len = a.length;
        int maxstart = 0, maxend = 1;
        // if char seen, store its position
        final HashMap<Character, Integer> seenChars = new HashMap<>();
        seenChars.put(a[0], 0);
        for (int i = 1, start = 0; i < len; ++i) {
            final char c = a[i];    // current char
            // check back for dups of current char
            final Integer prev = seenChars.get(c);
            if (prev != null) {
                final int newStart = prev + 1;
                do {    // we are moving the candidate interval, so remove sightings of chars in a[oldStart..prev]
                    seenChars.remove(a[start++]);
                } while (start < newStart);
            } else {    // no dups
                if (i + 1 - start > maxend - maxstart) {
                    maxstart = start;
                    maxend = i + 1;
                }
            }
            seenChars.put(c, i);
        }

        System.out.println("maxstart = " + maxstart);
        System.out.println("maxend = " + maxend);
        System.out.println("(Set) len = " + (maxend - maxstart));
        System.out.println(String.copyValueOf(a, maxstart, maxend - maxstart));
    }

    static void test(String input) {
        System.out.println("input = " + input);
        System.out.println("lwor(input) = " + lwor(input));
        linear(input.toCharArray());
        linearWithSet(input.toCharArray());
        System.out.println();
    }

    public static void main(String[] args) {
        test("xxabcyyyy");
        test("abccdefgh");
        test("abac");
        test("aaaaa");

//        test("");
        String input = Utils.randomString(50);
        test(input);

        String unicode = RandomStringUtils.random(10);
        System.out.println("unicode = " + unicode);
        test(unicode);
        System.out.println(RandomStringUtils.randomAlphanumeric(20));
    }
}
