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
    static void perm1(final String s) {
        perm1Aux("", s, s.length());
    }

    private static void perm1Aux(final String accum, final String remChars, final int remCharsLen) {
        if (remCharsLen == 0) {
            System.out.println(accum);
        } else {
            // every character has the chance to first/extend permutation accumulator
            // we create a tree of calls (think of each call as a node with children!)
            for (int i = 0; i < remCharsLen; i++) {
                perm1Aux(accum + remChars.charAt(i),   // use the char
                        remChars.substring(0, i) + remChars.substring(i + 1),   // remaining without the used one
                        remCharsLen - 1);
            }
        }
    }

    // print n! permutation of the elements of array a (not in order)
    static void perm2(String s) {
        perm2(s.toCharArray(), s.length());
    }

    // clarifies all code when this is extracted to a simple aux function
    private static void swap(char[] a, int i, int j) {
        final char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // again it is very helpful to think of the call tree as a tree
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
        perm2("ABCC");
        System.out.println("-------");
        perm1("ABCC");
    }
}
