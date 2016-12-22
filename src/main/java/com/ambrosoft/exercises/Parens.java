package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacek on 7/4/16.
 */

public class Parens {

    public static void main(String[] args) {
        List<String> result = generate(3);
        System.out.println("result = " + result);
    }

    static List<String> generate(final int n) {
        final ArrayList<String> result = new ArrayList<>();
        addParens(new char[2 * n], result, n, n, 0);
        return result;
    }

    private static void addParens(char[] chars, List<String> result, int lft, int rgt, int position) {
        System.out.println("lft = " + lft + "\trgt = " + rgt + "\t" + String.copyValueOf(chars, 0, position));
        if (lft == 0 && rgt == 0) {
            result.add(String.copyValueOf(chars));
        } else {
            if (lft > 0) {
                chars[position] = '(';
                addParens(chars, result, lft - 1, rgt, position + 1);
            }
            System.out.println("middle");
            if (rgt > lft) {
                chars[position] = ')';
                addParens(chars, result, lft, rgt - 1, position + 1);
            }
        }
    }
}
