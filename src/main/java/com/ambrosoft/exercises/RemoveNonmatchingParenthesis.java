package com.ambrosoft.exercises;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

/**
 * Created by jacek on 1/5/17.
 */
public class RemoveNonmatchingParenthesis {
    /*
    Given a string of parenthesis and characters, remove the invalid parentheses. eg. "(ab(a)" => "ab(a)"
     */

    static String remove(final String input) {
        final int len = input.length();
        final Deque<Integer> open = new ArrayDeque<>();
        final ArrayList<Integer> toRemove = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            switch (input.charAt(i)) {
                case '(':
                    open.push(i);
                    break;

                case ')':
                    if (open.isEmpty()) {
                        toRemove.add(i);    // closing out of place
                    } else {
                        open.pop(); // opening taken care of by this closing
                    }
                    break;
            }

        }

        toRemove.addAll(open);

        if (toRemove.isEmpty()) {
            return input;
        } else {
            final StringBuilder sb = new StringBuilder(input);
            Collections.sort(toRemove);
            int shift = 0;
            for (int index : toRemove) {
                sb.deleteCharAt(index - shift);
                shift++;
            }
            return sb.toString();
        }
    }

    static void test(String input) {
        System.out.println(String.format("%s -> %s", input, remove(input)));
    }

    public static void main(String[] args) {
        test("(ab(a)");
        test("))(ab(a)");
        test("))(ab(a))");
        test("))(ab(a)))");
        test("))(ab(a))))a");
        test("(a");
        test("a)");
        test("(a)");
        test(")a");
    }
}
