package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/9/16.
 * <p>
 * <p>
 * From Sedgewick
 */
public class Permutations {

    // JRA's ugly attempt
    static String[] permutations(String input) {

        final int length = input.length();
        if (length == 1) {
            return new String[]{input};
        } else {
            for (int i = 0; i < length; i++) {
                char c = input.charAt(i);
                String[] subPerm = permutations(input.substring(0, i) + input.substring(i + 1));

                String[] result = new String[subPerm.length * length];

                return result;
            }
        }
        return null;
    }


    // print n! permutation of the characters of the string s (in order)
    public static void perm1(String s) {
        perm1("", s, s.length());
    }

    private static void perm1(String accum, String remainingChars, int remainingCharsLen) {
        if (remainingCharsLen == 0) {
            System.out.println(accum);
        } else {
            // every character has the chance to start/extend  permutation accumulator
            for (int i = 0; i < remainingCharsLen; i++) {
                perm1(accum + remainingChars.charAt(i), remainingChars.substring(0, i) + remainingChars.substring(i + 1), remainingCharsLen - 1);
            }
        }
    }

    // print n! permutation of the elements of array a (not in order)
    static void perm2(String s) {
        perm2(s.toCharArray(), s.length());
    }

    private static void swap(char[] a, int i, int j) {
        final char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    static void perm2(final char[] a, final int n) {
        if (n == 1) {
            System.out.println(a);
        } else {
            for (int i = 0; i < n; i++) {
                swap(a, i, n - 1);
                perm2(a, n - 1);
                swap(a, i, n - 1);
            }
        }
    }


    public static void main(String[] args) {
        perm2("ABC");
        System.out.println("-------");
        perm1("ABC");
    }

}
