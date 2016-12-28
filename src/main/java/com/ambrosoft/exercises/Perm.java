package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/22/16.
 */
public class Perm {
    static void perm(final String input) {
        perm("", input, input.length());
    }

    private static void perm(final String accum, final String remaining, final int toUse) {
        if (toUse == 0) {
            System.out.println(accum);  // complete permutation
        } else {
            for (int i = 0; i < toUse; ++i) {
                final String prefix = accum + remaining.charAt(i);
                perm(prefix, remaining.substring(0, i) + remaining.substring(i + 1), toUse - 1);
            }
        }
    }

    public static void main(String[] args) {
        perm("(())");
    }
}
