package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 12/13/17
 * <p>
 * Atom: single capital letter or capital followed by lower-case, optionally followed by digits
 * Problem: write custom parser for groups; create a parse tree
 * It is effectively expression evaluator
 * <p>
 * Approach: think of grammar, propose overall structure
 * One could also create a tree, with internal nodes containing counts and elements in the leaves
 * For every leaf travel up multiplying counts along the path
 */
public class ParseMolecule {
    private static Pattern Tokenize = Pattern.compile("[A-Z][a-z]?|[\\d]+|[()\\[\\]{}]");

    enum TokenType {
        OPEN,
        CLOSE,
        ELEMENT,
        COUNT
    }

    private static class Token {
        final TokenType type;
        final String content;

        Token(TokenType tokenType, String content) {
            this.type = tokenType;
            this.content = content;
        }

        boolean equals(Token other) {
            return other.type == type && other.content.equals(content);
        }

        @Override
        public String toString() {
            return String.format("%s %s", type, content);
        }

        int toInt() {
            if (type == TokenType.COUNT) {
                return Integer.parseInt(content);
            } else {
                throw new IllegalStateException("non-count token toInt");
            }
        }

        Token matchingClose() {
            if (type == TokenType.OPEN) {
                switch (content.charAt(0)) {
                    case '(':
                        return new Token(TokenType.CLOSE, ")");
                    case '[':
                        return new Token(TokenType.CLOSE, "]");
                    case '{':
                        return new Token(TokenType.CLOSE, "}");
                    default:
                        throw new IllegalStateException("unknown open to close");

                }
            } else {
                throw new IllegalStateException("non-open token closing");
            }
        }
    }

    private static class Counts {
        private final HashMap<String, Integer> counts = new HashMap<>();

        void addCount(String symbol, int count) {
            final Integer extant = counts.get(symbol);
            counts.put(symbol, extant != null ? extant + count : count);
        }

        void multiply(int factor) {
            for (Map.Entry<String, Integer> element : counts.entrySet()) {
                counts.put(element.getKey(), element.getValue() * factor);
            }
        }

        void add(Counts other) {
            for (Map.Entry<String, Integer> element : other.counts.entrySet()) {
                final String key = element.getKey();
                counts.put(key, counts.getOrDefault(key, 0) + element.getValue());
            }
        }

        Map<String, Integer> getMap() {
            return counts;
        }

        @Override
        public String toString() {
            return counts.toString();
        }
    }

    private static Counts parseSeq(final Token[] tokens, int start) {
        final Counts counts = new Counts();
        while (start < tokens.length) {
            start = processToken(tokens, start, counts);
        }
        return counts;
    }

    private static int parseGroup(Token[] tokens, int start, Counts counts, Token close) {
        final Counts groupCounts = new Counts();
        while (start < tokens.length && !tokens[start].equals(close)) {
            start = processToken(tokens, start, groupCounts);
            if (start < tokens.length && tokens[start].equals(close)) {
                if (start + 1 < tokens.length && tokens[start + 1].type == TokenType.COUNT) {
                    groupCounts.multiply(tokens[start + 1].toInt());
                    counts.add(groupCounts);
                    return start + 2;
                } else {
                    counts.add(groupCounts);
                    return start + 1;
                }
            }
        }
        throw new IllegalArgumentException("not found " + close);
    }

    private static int processToken(Token[] tokens, int start, Counts counts) {
        switch (tokens[start].type) {
            case ELEMENT:
                if (start + 1 < tokens.length && tokens[start + 1].type == TokenType.COUNT) {
                    counts.addCount(tokens[start].content, tokens[start + 1].toInt());
                    return start + 2;
                } else {
                    counts.addCount(tokens[start].content, 1);
                    return start + 1;
                }

            case OPEN:
                return parseGroup(tokens, start + 1, counts, tokens[start].matchingClose());

            default:
                throw new IllegalArgumentException("unexpected token " + tokens[start]);
        }
    }

    private static Token[] tokenize(final String input) {
        final ArrayList<Token> list = new ArrayList<>();
        final Matcher tokens = Tokenize.matcher(input);
        while (tokens.find()) {
            final String fragment = tokens.group();
            switch (fragment.charAt(0)) {
                case '(':
                case '[':
                case '{':
                    list.add(new Token(TokenType.OPEN, fragment));
                    break;
                case ')':
                case ']':
                case '}':
                    list.add(new Token(TokenType.CLOSE, fragment));
                    break;
                default:
                    if (Character.isDigit(fragment.charAt(0))) {
                        list.add(new Token(TokenType.COUNT, fragment));
                    } else {
                        list.add(new Token(TokenType.ELEMENT, fragment));
                    }
            }
        }
        return list.toArray(new Token[list.size()]);
    }

    public static Map<String, Integer> getAtoms(String formula) {
        final Token[] tokens = tokenize(formula);
        if (tokens.length == 0) {
            throw new IllegalArgumentException(formula);
        } else {
            return parseSeq(tokens, 0).getMap();
        }
    }

    static void test(String input) {
        Token[] tokens = tokenize(input);
        System.out.println(Arrays.toString(tokens));
        Counts counts = parseSeq(tokens, 0);
        System.out.println("counts = " + counts);
    }

    public static void main(String[] args) {
//        test("Mg(OH");
//        test("pie");
        test("K4[ON(FeO3)2]2");
        test("O");
//        test("Mg(OH]2");
        test("H2O");
        test("K2(CO2)3");
    }
}
