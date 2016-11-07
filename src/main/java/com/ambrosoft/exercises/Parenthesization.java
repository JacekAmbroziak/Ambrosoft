package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacek on 10/22/16.
 */
public class Parenthesization {
    /*
        "5 * 4 - 3 * 2"
     */


    static abstract class Expr {
        abstract int eval();
    }

    private final static class Binary extends Expr {
        private final char op;
        private final Expr lft;
        private final Expr rgt;

        Binary(char op, Expr lft, Expr rgt) {
            this.op = op;
            this.lft = lft;
            this.rgt = rgt;
        }

        @Override
        int eval() {
            final int l = lft.eval();
            final int r = rgt.eval();
            switch (op) {
                case '+':
                    return l + r;
                case '-':
                    return l - r;
                case '*':
                    return l * r;
                default:
                    throw new Error();
            }
        }

        @Override
        public String toString() {
            return "(" + lft + op + rgt + ')';
        }
    }

    static class Constant extends Expr {
        final int value;

        Constant(String value) {
            this.value = Integer.parseInt(value);
        }

        @Override
        int eval() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    static Expr[] buildExpressions(String[] tokens, int first, int last) {
        if (first == last) {
            return new Expr[]{new Constant(tokens[first])};
        } else {
            final ArrayList<Expr> list = new ArrayList<>();
            // odd node of tokens
            // every operator has a chance of becoming a root
            for (int i = first + 1; i < last; i += 2) {
                final char op = tokens[i].charAt(0);
                final Expr[] left = buildExpressions(tokens, first, i - 1);
                final Expr[] right = buildExpressions(tokens, i + 1, last);
                for (Expr lft : left) {
                    for (Expr rgt : right) {
                        list.add(new Binary(op, lft, rgt));
                    }
                }
            }
            return list.toArray(new Expr[list.size()]);
        }
    }

    static int indexOfOperator(final String text, int index, int end) {
        while (index < end) {
            switch (text.charAt(index)) {
                case '+':
                case '-':
                case '*':
                    return index;

                default:
                    ++index;
                    break;
            }
        }
        return -1;
    }

    // in contrast to my early incorrect intuition, problems do overlap
    // and the use of a cache makes perfect sense
    // as the position of a splitting/root/middle operator moves from left to right
    // substrings to the left and right of the operator are parsed and re-parsed multiple times
    // encountering the same constants & bigger expressions multiple times
    // it is also possible (like Geeks solution) to evaluate expressions & not construct trees
    private static Expr[] buildExpressions(String text, int start, int end, Map<String, Expr[]> memo) {
        final String fragment = text.substring(start, end);
        Expr[] result = memo.get(fragment);
        if (result == null) {   // not yet computed
            int nextOp = indexOfOperator(text, start, end);
            if (nextOp < 0) {
                result = new Expr[]{new Constant(fragment)};
            } else {
                final ArrayList<Expr> list = new ArrayList<>();
                // every operator has a chance of becoming a root
                do {
                    final char op = text.charAt(nextOp);
                    final Expr[] left = buildExpressions(text, start, nextOp, memo);
                    final Expr[] right = buildExpressions(text, nextOp + 1, end, memo);
                    for (Expr lft : left) {
                        for (Expr rgt : right) {
                            list.add(new Binary(op, lft, rgt));
                        }
                    }
                } while ((nextOp = indexOfOperator(text, nextOp + 1, end)) > 0);
                result = list.toArray(new Expr[list.size()]);
            }
            // cache it
            memo.put(fragment, result);
        }
        return result;
    }

    private static int[] buildExpressionsEval(String text, int start, int end, Map<String, int[]> memo) {
        final String fragment = text.substring(start, end);
        int[] result = memo.get(fragment);
        if (result == null) {   // not yet computed
            int nextOp = indexOfOperator(text, start, end);
            if (nextOp < 0) {
                result = new int[]{Integer.parseInt(fragment)};
            } else {
                final ArrayList<Integer> list = new ArrayList<>();
                // every operator has a chance of becoming a root
                do {
                    final char op = text.charAt(nextOp);
                    final int[] left = buildExpressionsEval(text, start, nextOp, memo);
                    final int[] right = buildExpressionsEval(text, nextOp + 1, end, memo);

                    switch (op) {
                        case '+':
                            for (int lft : left) {
                                for (int rgt : right) {
                                    list.add(lft + rgt);
                                }
                            }
                            break;

                        case '-':
                            for (int lft : left) {
                                for (int rgt : right) {
                                    list.add(lft - rgt);
                                }
                            }
                            break;

                        case '*':
                            for (int lft : left) {
                                for (int rgt : right) {
                                    list.add(lft * rgt);
                                }
                            }
                            break;

                        default:
                            throw new Error();
                    }
                } while ((nextOp = indexOfOperator(text, nextOp + 1, end)) > 0);
                result = new int[list.size()];
                for (int i = result.length; --i >= 0; ) {
                    result[i] = list.get(i);
                }
            }
            // cache it
            memo.put(fragment, result);
        }
        return result;
    }

    static void parse(String input) {
        String[] tokens = input.split(" ");
        Expr[] exprs = buildExpressions(tokens, 0, tokens.length - 1);
        for (Expr expr : exprs) {
            int val = expr.eval();
            System.out.println("expr = " + expr);
            System.out.println("val = " + val);
        }
    }


    // the version using the original string input has advantages over tokenization
    // for example substrings make good keys to cache parse trees
    static void parse2(String input) {
        final Map<String, Expr[]> memo = new HashMap<>();
        final Expr[] exprs = buildExpressions(input, 0, input.length(), memo);
        for (Expr expr : exprs) {
//            int val = expr.eval();
//            System.out.println("expr = " + expr);
//            System.out.println("val = " + val);
        }
        System.out.println("exprs.length = " + exprs.length);
    }

    static void parse3(String input) {
        final Map<String, int[]> memo = new HashMap<>();
        final int[] exprs = buildExpressionsEval(input, 0, input.length(), memo);
        for (int expr : exprs) {
//            int val = expr.eval();
//            System.out.println("expr = " + expr);
//            System.out.println("val = " + val);
        }
        System.out.println("exprs3.length = " + exprs.length);
    }


    public static void main(String[] args) {
        parse("5 * 4 - 3 * 2");
        parse("3 - 2 - 1");

//        parse2("5*4-3*2");
//        parse2("3-2-1");
//        parse2("5*4");
//        parse2("5*4+3");
//        parse2("5*4+3-2");
//        parse2("5*4+3-2*1");
//        parse2("5*4+3-2*1+7");
//        parse2("5*4+3-2*1+7*9");
//        parse2("5*4+3-2*1+7*9+3");
//        parse2("5*4+3-2*1+7*9+3+7*8");
//        parse2("5*4+3-2*1+7*9+3+7*8+1");
        parse2("5*4+3-2*1+7*9+3+7*8+1*2");
        parse3("5*4+3-2*1+7*9+3+7*8+1*2");
    }
}
