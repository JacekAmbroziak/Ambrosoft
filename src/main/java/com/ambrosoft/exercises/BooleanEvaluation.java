package com.ambrosoft.exercises;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

/**
 * Created by jacek on 12/25/16.
 */
public class BooleanEvaluation {
    /*
        Count number of ways expression can be parenthesized to evaluate to given value

        Parenthesisation corresponds to a shape of a binary tree where 0,1 constants are leaves
        and all operands are internal nodes
        there will be n internal nodes including root for n operands
        We need to be able to search the space of all such trees
        One can also think in terms of injecting n pairs of ()s into the original string
        ... or indeed grouping by available operators
        need to make progress: choices, search space factorization, splitting into subproblems, families

        n! total ways (w/o filtering): not true: Catalan numbers

        My method was not correct (unless deduped with a Set) and definitely inefficient
     */

    // assuming no spaces and 1-char tokens: create one String for each char
    private static String[] parse(final String expr) {
        final char[] chars = expr.toCharArray();
        final String[] exprs = new String[chars.length];
        for (int i = 0; i < chars.length; ++i) {
            exprs[i] = String.valueOf(chars, i, 1);
        }
        return exprs;
    }

    private static int countWays(String expr, boolean target) {
        HashSet<String> set = new HashSet<>();
        return countWays(parse(expr), target ? 1 : 0, set);
    }

    // eval in String domain
    private static String eval(String lft, String op, String rgt) {
        boolean L = lft.equals("1");
        boolean R = rgt.equals("1");
        final boolean result;
        switch (op.charAt(0)) {
            case '&':
                result = L & R;
                break;
            case '^':
                result = L ^ R;
                break;
            case '|':
                result = L | R;
                break;
            default:
                throw new Error();
        }
        return result ? "1" : "0";
    }

    // lft,rgt: 0 or 1
    private static int eval(final int lft, final char op, final int rgt) {
        switch (op) {
            case '|':
                return lft | rgt;

            case '&':
                return lft & rgt;

            case '^':
                return lft ^ rgt;

            default:
                throw new IllegalArgumentException();
        }
    }

    // this could be made faster as tokens are always single chars
    private static String parenthesize(final String[] tokens, int opIndex) {
        return new StringBuilder(8).append('(')
                .append(tokens[opIndex - 1])
                .append(tokens[opIndex])
                .append(tokens[opIndex + 1]).append(')').toString();
    }

    // the method I came up with is not correct (unless deduped with a Set)
    // and grossly inefficient
    // I am not using target until the very end
    // and I am also forgetting that I need to *count* stuff (by any method) not necessarily *enumerate*
    // the problem was listed in the recursion/DP section, I forgot to think about it via D&C
    private static int countWays(final String[] exprs, final int target, final HashSet<String> set) {
        final int len = exprs.length;
        if (len == 1) { // fully reduced to 1 parenthesized expression
            final String expr = exprs[0];
            System.out.println(expr);
            if (set.contains(expr)) {
                System.out.println("already known " + expr);
                return 0;
            } else {
                set.add(expr);
            }
            final int res = evaluate(expr);
            System.out.println(res);
            return target == res ? 1 : 0;
        } else {
            int count = 0;
            for (int opidx = 1; opidx < len; opidx += 2) {  // index just the operators
                // group around opidx
                final String[] grouped = new String[len - 2];   // 3 tokens to be replacd by 1 expression
                System.arraycopy(exprs, 0, grouped, 0, opidx - 1);
                grouped[opidx - 1] = parenthesize(exprs, opidx);
//                grouped[opidx - 1] = eval(exprs[opidx - 1], exprs[opidx], exprs[opidx + 1]);
                System.arraycopy(exprs, opidx + 2, grouped, opidx, len - opidx - 2);

                count += countWays(grouped, target, set);
            }
            return count;
        }
    }

    private static int countWaysToParenthesize(final String expression) {
        int len = expression.length();
        if (len < 5) {  // eg. "1" or 1^0"
            return 1;
        } else {
            int count = 0;
            for (int op = 1; op < len; op += 2) {   // index just the operators
                int lft = countWaysToParenthesize(expression.substring(0, op));
                int rgt = countWaysToParenthesize(expression.substring(op + 1));
                count += lft * rgt;
            }
            return count;
        }
    }

    // based on GeeksForGeeks
    static int evaluate(final String expression) {
        final int length = expression.length();
        // operand stack
        final Deque<Character> opStack = new ArrayDeque<>();
        // value stack: literals and evaluated
        final Deque<Integer> valueStack = new ArrayDeque<>();
        for (int i = 0; i < length; i++) {
            switch (expression.charAt(i)) {
                case '(':
                    opStack.push('(');  // will serve as marker
                    break;

                case ')':   // don't push: start evaluation
                    while (opStack.peek() != '(') {
                        valueStack.push(eval(valueStack.pop(), opStack.pop(), valueStack.pop()));
                    }
                    opStack.pop();
                    break;

                case '0':
                    valueStack.push(0);
                    break;

                case '1':
                    valueStack.push(1);
                    break;

                case '|':
                    opStack.push('|');
                    break;

                case '&':
                    opStack.push('&');
                    break;

                case '^':
                    opStack.push('^');
                    break;

                case ' ':
                    break;
            }
        }

        while (!opStack.isEmpty()) {
            valueStack.push(eval(valueStack.pop(), opStack.pop(), valueStack.pop()));
        }
        return valueStack.pop();
    }

    static void testEval(String expr) {
        int res = evaluate(expr);
        System.out.println(String.format("%s -> %d", expr, res));
    }

    static void testCount(final String expr) {
        int res = countWaysToParenthesize(expr);
        System.out.println(String.format("%s -> %d", expr, res));
    }

    static void test(String expr, boolean target) {
        int res = countWays(expr, target);
        System.out.println(String.format("%s -> %b (%d)", expr, target, res));
    }

    public static void main(String[] args) {
//        test("1^0|0|1", false);
        test("0&0&0&1^1|0", true);

//        testEval("(1 & 1)");
//        testEval("(1 & 0)");
//        testEval("(1 | 0)");
//        testEval("(1 ^ 0)");
//        testEval("(1 ^ 0) | 0");

//        testCount("1^0|0|1");
        testCount("0&0&0&1^1|0");
//        testCount("1^0&0|1&0|1^0|1&0");

//        System.out.println("allTrees() = " + allTrees(5));
    }
}
