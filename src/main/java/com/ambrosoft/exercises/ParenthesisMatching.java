package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/4/16.
 * <p>
 * Uber problem, 15 min time
 */
public class ParenthesisMatching {
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

    static boolean balanced2(final String input) {
        final int len = input.length();
        if (len < 2) {
            return true;
        } else {
            final int[] stack = new int[len];
            int sp = 0;
            for (int i = 0; i < len; i++) {
                final char c = input.charAt(i);
                switch (c) {
                    case '(':
                        stack[sp++] = ')';
                        break;

                    case '[':
                        stack[sp++] = ']';
                        break;

                    case '{':
                        stack[sp++] = '}';
                        break;

                    case ')':
                    case ']':
                    case '}':
                        if (sp > 0 && stack[sp - 1] == c) { // has to have matching on top
                            --sp;   // cancel out, pop
                        } else {
                            return false;
                        }
                        break;
                }
            }
            return sp == 0; // no delims or pairs cancelled out
        }
    }

    static void test(String input) {
        System.out.println("input = " + input);
        System.out.println("balanced1(input) = " + balanced(input));
        System.out.println("balanced2(input) = " + balanced2(input));
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
