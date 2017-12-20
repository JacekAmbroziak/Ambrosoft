package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 12/17/17
 */
public class ParseMoleculeBackwards {
    private static Pattern Tokenize = Pattern.compile("[A-Z][a-z]?|[\\d]+|[()\\[\\]{}]");
    private static int[] OPENING_FOR = new int[128];    // matching opening paren

    static {
        OPENING_FOR[')'] = '(';
        OPENING_FOR[']'] = '[';
        OPENING_FOR['}'] = '{';
    }

    private static String[] tokenize(final String input) {
        final ArrayList<String> list = new ArrayList<>();
        for (final Matcher tokens = Tokenize.matcher(input); tokens.find(); ) {
            list.add(tokens.group());
        }
        return list.toArray(new String[list.size()]);
    }

    /*
        when reading right to left we know group multipliers and atom counts before encountering elements
        so upon encountering element symbol we know its count at that point
     */
    public static Map<String, Integer> getAtoms(final String formula) {
        final String[] tokens = tokenize(formula);
        if (tokens.length == 0) {
            throw new IllegalArgumentException(formula);
        } else {
            final HashMap<String, Integer> counts = new HashMap<>();
            final int[] stack = new int[32], opening = new int[32];
            int sp = -1;    // stack pointer
            int count = 1;
            int multiplier = 1;
            for (int i = tokens.length; --i >= 0; ) {
                final String token = tokens[i];
                final char firstChar = token.charAt(0);
                if (Character.isLetter(firstChar)) {    // element symbol
                    counts.put(token, counts.getOrDefault(token, 0) + multiplier * count);
                    count = 1;
                } else if (Character.isDigit(firstChar)) {
                    count = Integer.parseInt(token);    // new count to be applicable to atom or group to the left of it
                } else {
                    switch (firstChar) {
                        case ')':
                        case ']':
                        case '}':
                            multiplier *= count;    // effective atom multiplier within the group
                            stack[++sp] = count;    // store this group's count to cancel at group's end
                            count = 1;
                            opening[sp] = OPENING_FOR[firstChar];   // store expected matching group opening paren type
                            break;
                        case '(':
                        case '[':
                        case '{':
                            if (sp < 0 || opening[sp] != firstChar) {
                                throw new IllegalArgumentException("unmatched bracket");
                            }
                            multiplier /= stack[sp--];
                            break;

                        default:
                            throw new IllegalArgumentException("unexpected character in formula " + firstChar);
                    }
                }
            }
            if (sp >= 0) {
                throw new IllegalArgumentException("unbalanced parentheses");
            }
            return counts;
        }
    }

    /*
        simple form of the right-to-left algorithm for well formed formulae using only parentheses
     */
    public static Map<String, Integer> getAtoms_simple(final String formula) {
        final String[] tokens = tokenize(formula);
        final HashMap<String, Integer> counts = new HashMap<>();
        final int[] stack = new int[32];
        int sp = -1;    //stack pointer
        for (int count = 1, multiplier = 1, i = tokens.length; --i >= 0; ) {
            final String token = tokens[i];
            final char firstChar = token.charAt(0);
            if (Character.isLetter(firstChar)) {
                counts.put(token, counts.getOrDefault(token, 0) + multiplier * count);
                count = 1;  // count has been used and goes back to default
            } else if (Character.isDigit(firstChar)) {
                count = Integer.parseInt(token);    // new count to be applicable to atom or group to the left of it
            } else {
                switch (firstChar) {
                    case ')':   // reading right to left, ) opens a group
                        multiplier *= count;    // effective atom multiplier within the group
                        stack[++sp] = count;    // store this group's count to cancel at group's end
                        count = 1;
                        break;
                    case '(':
                        multiplier /= stack[sp--];  // up one level; back to previous multiplier
                        break;
                }
            }
        }
        return counts;
    }

    static void test(String input) {
        String[] tokens = tokenize(input);
        System.out.println(Arrays.toString(tokens));
        Map<String, Integer> counts = getAtoms(input);
        System.out.println(counts);
    }

    public static void main(String[] args) {
//        test("K4[ON(SO3)2]2");
        test("MgOH)2");

//        test("K4(ON(SO3)2)2");

//        test("H3((S2O)7)2K5");
    }
}
