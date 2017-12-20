package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 12/16/17
 *
 * Factors/multiplies of atom counts are on upward paths from leaves to root
 * One couls also read tokens from right to left and have all the counts in one pass
 */
public class ParseMoleculeTree {
    private static Pattern Tokenize = Pattern.compile("[A-Z][a-z]?|[\\d]+|[()\\[\\]{}]");
    private static int[] CLOSING = new int[128];

    static {
        CLOSING['('] = ')';
        CLOSING['['] = ']';
        CLOSING['{'] = '}';
    }

    static class Node {
        final Node parent;
        final String element;   // atomic symbol for leaves, null for parenthesized group
        int factor = 1;         // default single atom or group unless overridden by count in formula

        Node(Node parent, String element) {
            this.parent = parent;
            this.element = element;
        }

        int ancestryPathProduct() {
            return parent != null ? factor * parent.ancestryPathProduct() : factor;
        }
    }

    private static String[] tokenize(final String input) {
        final ArrayList<String> list = new ArrayList<>();
        for (final Matcher tokens = Tokenize.matcher(input); tokens.find(); ) {
            list.add(tokens.group());
        }
        return list.toArray(new String[list.size()]);
    }

    static Map<String, Integer> buildTree(String[] tokens) {
        final ArrayList<Node> leaves = new ArrayList<>();
        // create tree, gather leaves
        withParent(new Node(null, null), tokens, 0, 0, leaves);
        final HashMap<String, Integer> result = new HashMap<>();
        for (final Node leaf : leaves) {  // traverse paths up from leaves, multiply ancestors & add up counts
            result.put(leaf.element, result.getOrDefault(leaf.element, 0) + leaf.ancestryPathProduct());
        }
        return result;
    }

    // return new 'start'
    private static int withParent(Node parent, String[] tokens, int start, final int closingParen, ArrayList<Node> leaves) {
        for (Node lastNode = null; start < tokens.length; ++start) {
            final String currentToken = tokens[start];
            final char firstChar = currentToken.charAt(0);
            if (Character.isLetter(firstChar)) {
                leaves.add(lastNode = new Node(parent, currentToken));    // create element leaf node
            } else if (Character.isDigit(firstChar)) {
                if (lastNode != null) {
                    lastNode.factor = Integer.parseInt(currentToken);   // count applies to last Node created at this level
                } else {
                    throw new IllegalArgumentException("count w/o element or group");
                }
            } else {
                switch (firstChar) {
                    case '(':
                    case '[':
                    case '{':   // recurse to process group with child of this parent as parent for the group
                        start = withParent(lastNode = new Node(parent, null), tokens, start + 1, CLOSING[firstChar], leaves);
                        break;
                    case ')':
                    case ']':
                    case '}':
                        if (firstChar == closingParen) {
                            return start;
                        } else {
                            throw new IllegalArgumentException("wrong group closing");
                        }

                    default:
                        throw new IllegalArgumentException("unexpected character in formula");
                }
            }
        }
        if (closingParen != 0) {
            throw new IllegalArgumentException("group not closed");
        }
        return start;
    }

    public static Map<String, Integer> getAtoms(final String formula) {
        final String[] tokens = tokenize(formula);
        if (tokens.length == 0) {
            throw new IllegalArgumentException(formula);
        } else {
            final ArrayList<Node> leaves = new ArrayList<>();
            // create tree, gather leaves
            withParent(new Node(null, null), tokens, 0, 0, leaves);
            final HashMap<String, Integer> result = new HashMap<>();
            for (final Node leaf : leaves) {    // traverse paths up from leaves, multiply ancestors & add up counts
                result.put(leaf.element, result.getOrDefault(leaf.element, 0) + leaf.ancestryPathProduct());
            }
            return result;
        }
    }

    static void test(String input) {
        String[] tokens = tokenize(input);
        System.out.println(Arrays.toString(tokens));
        Map<String, Integer> counts = getAtoms(input);
        System.out.println(counts);
    }

    public static void main(String[] args) {
        test("K4[ON(SO3)2]2");
        test("H2O");
        test("H");
//        test("pie");
        test("Mg(OH)");
    }

}
