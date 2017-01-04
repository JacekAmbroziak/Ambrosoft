package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/29/16.
 * <p>
 * https://community.topcoder.com/stat?c=problem_statement&pm=14391
 * <p>
 * Minimal edit of a string so that it is of the form T + T
 */
public class RepeatString {

    static int minimalModify(final String a) {
        final int alen = a.length();
        if (alen == 0) {
            return 0;   // already repeat string
        } else if (alen == 1) {
            return 1;   // just double the char
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < alen - 1; i++) {
                final String lft = a.substring(0, i);
                final String rgt = a.substring(i);
                min = Math.min(min, EditDistance.editDistance(lft, rgt));
            }
            return min;
        }
    }

    static void test(String a) {
        System.out.println(String.format("%s -> %d", a, minimalModify(a)));
    }

    public static void main(String[] args) {
        test("adam");
        test("aba");
        test("x");
        test("aaabbbaaaccc");
        test("repeatstring");
        test("aaaaaaaaaaaaaaaaaaaa");
    }
}
