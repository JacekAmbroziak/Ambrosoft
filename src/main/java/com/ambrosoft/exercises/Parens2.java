package com.ambrosoft.exercises;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacek on 12/23/16.
 */
public class Parens2 {
    /*
        All valid combinations of n pairs of ()

        Have 3 pairs:

        1 pair + 2 remaining
            can use the 1st pair to just output it
                of 2 remaining, take 1
                    output, or
                    push
            or to push level
                of 2 remaining, take 1
                    output, or
                        push

        ()()(), (())(), ()(()), (()()), ((()))

        DUDUDU, DDUUDU DUDDUU DDUDUU DDDUUU

        Two rules at play: nesting and series (or vertical and horizontal)

        HH. V.H HV. VH. VV.

        For 1 pair: ()
        For 2 pairs: ()()  (())  or H. V.

        New pair () can be added to () -- inside (()) or next to ()()
        New pair () can be added to ()()+(())

            ()() -> ()()() next, (()()) surround
            (()) -> (())() next, ((())) surround
            (()) -> ()(()) before

        try side, try nest

        S3, N2 S1, S1 N2, N (S S), N(N)

        111 21 12 2

        This corresponds to different shapes of binary trees with root acting like handle (not generating parens)
        Pair distributions: 1,1,1 | (2),1 | 1,(2) | 1(1,1) | 1(1(1))
     */


    static void foo(int rem, int level, StringBuilder sb) {
        if (rem > 0) {
            sb.append('(').append(')');
            foo(rem - 1, level, sb);
            sb.append('|');
            sb.append('(');
            foo(rem - 1, level + 1, sb);
            sb.append(')');

        }
    }


    static void parens(int n) {
        StringBuilder sb = new StringBuilder();

//        parens(sb, n, 0);

        foo(n, 0, sb);


        String result = sb.toString();
        System.out.println(result);
    }

    // depth & remaining

    private static void parens(StringBuilder sb, int n, int depth) {
        if (n == 0) {
            return;
        } else {
            if (n == 1) {
                sb.append('(').append(')');
            } else {

            }
        }
    }

    // two modes: nesting & sideways

    static void nest(int n, StringBuilder sb) {
        if (n > 0) {
            sb.append('(');

            for (int i = 1; i < n; i++) {

            }

            sb.append(')');
        }
    }

    static void side(int n, StringBuilder sb) {
        if (n > 0) {
            sb.append('(');
            sb.append(')');

        }
    }


    static Set<String> generate(Set<String> input) {
        HashSet<String> result = new HashSet<>();

        for (String expr : input) {
            result.add("()" + expr);
            result.add(expr + "()");
            result.add('(' + expr + ')');
        }

        return result;
    }

    public static void main(String[] args) {
//        parens(3);


        Set<String> level1 = new HashSet<>();
        level1.add("()");

        Set<String> level2 = generate(level1);
        Set<String> level3 = generate(level2);
        Set<String> level4 = generate(level3);
        Set<String> level5 = generate(level4);

        System.out.println("level3 = " + level3);
        System.out.println("level4 = " + level4);
        System.out.println("level4 = " + level4.size());
        System.out.println("level5 = " + level5);
        System.out.println("level5 = " + level5.size());

    }


}
