package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacek on 12/23/16.
 */
public class PermutationsNoDups {

    // can also be immutable
    static class CharCount {
        final char c;
        final int count;

        CharCount(char c, int count) {
            this.c = c;
            this.count = count;
        }

        char getChar() {
            return c;
        }

        @Override
        public String toString() {
            return String.format("['%c', %d]", this.c, this.count);
        }

        CharCount decrement() {
            return new CharCount(c, count - 1);
        }

        // in Scala we would have Option(count) or None
        // here we can have a sentinel with const 0, or null
        CharCount decrement1() {
            final int newCount = count - 1;
            return newCount != 0 ? new CharCount(c, newCount) : null;
        }
    }

    static void permutations(final String input) {
        final char[] chars = input.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        System.out.println("sorted = " + sorted);

        final List<CharCount> charCounts = new ArrayList<>();
        char lastChar = sorted.charAt(0);
        int count = 1;
        for (int i = 1; i < sorted.length(); ++i) {
            final char cc = sorted.charAt(i);
            if (cc == lastChar) {
                ++count;
            } else {
                charCounts.add(new CharCount(lastChar, count));
                lastChar = cc;
                count = 1;
            }
        }
        // not forgetting the last group
        charCounts.add(new CharCount(lastChar, count));

        System.out.println("charCounts = " + charCounts);
        permutations(charCounts);
    }

    private static int charsRemaining(final List<CharCount> charCounts) {
        int sum = 0;
        for (CharCount charCount : charCounts) {
            sum += charCount.count;
        }
        return sum;
    }

    private static void permutations(final List<CharCount> charCounts) {
        permutations("", charCounts, charsRemaining(charCounts));
    }

    // make a copy of the list with ith CharCount decremented
    // it could also just be removed from copy
    private static List<CharCount> decrement(List<CharCount> charCounts, int i) {
        final List<CharCount> smaller = new ArrayList<>(charCounts);
        smaller.set(i, smaller.get(i).decrement());
        return smaller;
    }

    private static List<CharCount> decrement1(List<CharCount> charCounts, int i) {
        final List<CharCount> smaller = new ArrayList<>(charCounts);
        final CharCount charCount = smaller.get(i).decrement1();
        if (charCount != null) {
            smaller.set(i, charCount);
        } else {
            smaller.remove(i);
        }
        return smaller;
    }

    /*
        Approach:
        similar to permutations of unique chars
        each level of recursion adds one more char to accumulator from each of the remaining groups
        loop extends prefix by a char from a non-empty group
        recursion continues w/ decremented list of groups
        Because all chars need to be used in permutation (same length as input string), the depth of recursion
        is the same as total char count
        Each recursion level receives extended prefix & decremented remaining
     */

    private static void permutations(String accum, final List<CharCount> charCounts, int toUse) {
        if (toUse == 0) {
            System.out.println(accum);  // complete
        } else {
            // every remaining, non-zero, CharCount can extend accumulator
            for (int i = 0; i < charCounts.size(); ++i) {
                final CharCount charCount = charCounts.get(i);
                final String prefix = accum + charCount.getChar();
                permutations(prefix, decrement1(charCounts, i), toUse - 1);
            }
        }
    }

    static void permGayle(final String input) {
        final HashMap<Character, Integer> counts = new HashMap<>();
        for (final Character character : input.toCharArray()) {
            final Integer count = counts.get(character);
            if (count != null) {
                counts.put(character, count + 1);
            } else {
                counts.put(character, 1);
            }
        }
        permGayle("", counts, input.length());
    }

    private static void permGayle(final String prefix, final HashMap<Character, Integer> counts, final int rem) {
        if (rem == 0) {
            System.out.println(prefix);
        } else {
            for (final Character character : counts.keySet()) {
                final Integer count = counts.get(character);
                if (count > 0) {
                    counts.put(character, count - 1);
                    permGayle(prefix + character, counts, rem - 1);
                    counts.put(character, count);   // restore
                }
            }
        }
    }

    public static void main(String[] args) {
        permutations("CBABCC");
        permutations("ABC");
        permGayle("CBABCC");
        permutations("AAA");
        permutations("((()))");
    }
}
