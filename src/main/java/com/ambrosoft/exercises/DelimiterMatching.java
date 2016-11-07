package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/4/16.
 *
 * Uber problem, 15 min time
 */
public class DelimiterMatching {
    // matching delimiters
    static String openDelim = "([{";
    static String closeDelim = ")]}";

    static boolean balanced(final String input) {
        final int len = input.length();
        if (len < 2) {
            return true;
        } else {
            final int[] stack = new int[len];
            int sp = 0;
            for (int i = 0; i < len; i++) {
                final char c = input.charAt(i);
                final int open = openDelim.indexOf(c);
                if (open >= 0) {
                    stack[sp++] = open; // only opening are pushed on stack
                } else {
                    final int close = closeDelim.indexOf(c);
                    if (close >= 0) {
                        if (sp > 0 && stack[sp - 1] == close) { // has to have matching opening on top
                            --sp;   // cancel out, pop
                        } else {
                            return false;
                        }
                    }
                }
            }
            return sp == 0; // no delims or pairs cancelled out
        }
    }

    static void test(String input) {
        System.out.println("input = " + input);
        System.out.println("balanced(input) = " + balanced(input));
    }

    public static void main(String[] args) {
        test("{ac[bb]}");
        test("{df][d}");
        test("[dklf(df(kl))d]{}");
        test("()");
        test("[]");
        test("a[]b()");
        test("[)");
    }
}
