package com.ambrosoft.exercises;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created on 12/9/17
 */
public class BraceChecker {
    private final static String OPEN = "([{";
    private final static String CLOSE = ")]}";

    public boolean isValid(final String braces) {
        if (braces == null) {
            throw new IllegalArgumentException();
        } else {
            final int length = braces.length();
            if (length > 0) {
                final Deque<Integer> stack = new LinkedList<>();
                for (int i = 0; i < length; i++) {
                    final char c = braces.charAt(i);
                    final int open = OPEN.indexOf(c);
                    if (open >= 0) {
                        stack.push(open);
                    } else {
                        final int close = CLOSE.indexOf(c);
                        if (close >= 0) {
                            if (stack.isEmpty() || stack.pop() != close) {
                                return false;
                            }
                        }
                    }
                }
                if (!stack.isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean isValid_malacath(String braces) {
        if (braces.equals("")) {
            return true;
        } else {
            Deque<Character> stack = new ArrayDeque<>();
            for (char c : braces.toCharArray()) {
                switch (c) {
                    case '(':
                        stack.push(')');
                        break;
                    case '[':
                        stack.push(']');
                        break;
                    case '{':
                        stack.push('}');
                        break;
                    case ']':
                    case ')':
                    case '}':
                        if (stack.isEmpty() || stack.pop() != c) {
                            return false;
                        }
                }
            }
            return stack.isEmpty();
        }
    }

    public static void main(String[] args) {
        BraceChecker checker = new BraceChecker();
        boolean result = checker.isValid_malacath("[()]()(");
        System.out.println("result = " + result);
    }
}
