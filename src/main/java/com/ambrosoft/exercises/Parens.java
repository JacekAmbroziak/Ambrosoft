package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacek on 7/4/16.
 */

public class Parens {

    public static void main(String[] args) {
        List<String> result = generate(4);
        System.out.println("result = " + result);
        System.out.println("result = " + result.size());
    }

    static List<String> generate(final int n) {
        final ArrayList<String> result = new ArrayList<>();
        addParens(new char[2 * n], result, n, n, 0);
        return result;
    }

    // chars: reusable char array where (s and )s are placed at position which grows with recursion level
    // lft, rgt: numbers of remaining parenthesis
    // the general shape of the recursive tree (as shown with addParensAny) allows generation of all possibilities
    // if clauses constrain the generation to only () matching forms
    // the general, unconstrained, generation reaches every possibility, only once
    // the 'if's ensure that only n ('s will be written, and n )'s when there's something open
    // the question asked each time is: can we extend the sequence with ( or )?
    // the answer is, yes we can extend with ( if we still have them
    // and with ) if more ( than ) written so far

    private static void addParens(char[] chars, List<String> result, int lftRem, int rgtRem, int index) {
        if (index == chars.length) {
            result.add(String.copyValueOf(chars));
        } else {
            if (lftRem > 0) {
                chars[index] = '(';
                addParens(chars, result, lftRem - 1, rgtRem, index + 1);
            }
            if (rgtRem > lftRem) {
                chars[index] = ')';
                addParens(chars, result, lftRem, rgtRem - 1, index + 1);
            }
        }
    }

    private static void addParensAny(char[] chars, List<String> result, int lft, int rgt, int index) {
        if (index == chars.length) {
            result.add(String.copyValueOf(chars));
        } else {
            if (true) {
                chars[index] = '(';
                addParensAny(chars, result, lft - 1, rgt, index + 1);
            }
            if (true) {
                chars[index] = ')';
                addParensAny(chars, result, lft, rgt - 1, index + 1);
            }
        }
    }
}
