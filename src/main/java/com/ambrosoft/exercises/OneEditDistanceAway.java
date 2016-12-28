package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/5/16.
 */
public class OneEditDistanceAway {
    /*
        insert, remove, replace
        checking for edit distance at most 1
     */

    static boolean isMaxOneEditAway(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException();
        } else if (a.equals(b)) {
            return true;
        } else {
            final int alen = a.length();
            final int blen = b.length();
            // early modularization (aux functions) very helpful!
            if (alen == blen) {
                return checkForOneReplacement(a, b);
            } else if (alen < blen) {
                return checkForOneInsert(a, b);
            } else {
                return checkForOneInsert(b, a); // another benefit of aux function
            }
        }
    }

    // b is always longer
    private static boolean checkForOneInsert(String a, String b) {
        final int alen = a.length();
        final int blen = b.length();
        if (alen + 1 == blen) {
            int i = 0, j = 0;
            do {
                if (a.charAt(i++) != b.charAt(j++)) {
                    ++j;
                }
            } while (i < alen && j < blen);
            return j <= i + 1;
        } else {
            return false;
        }
    }

    // equal length strings
    private static boolean checkForOneReplacement(String a, String b) {
        boolean replacement = false;
        for (int i = a.length(); --i >= 0; ) {
            if (a.charAt(i) != b.charAt(i)) {
                if (replacement) {
                    return false;   // already had one
                } else {
                    replacement = true; // first difference
                }
            }
        }
        return replacement;
    }

    static void test(String a, String b) {
        System.out.println(String.format("%s %s -> %b", a, b, isMaxOneEditAway(a, b)));
    }

    public static void main(String[] args) {
        test("pales", "pale");
        test("spale", "pale");
        test("pale", "ple");
        test("pale", "bale");
        test("pale", "bae");
    }
}
