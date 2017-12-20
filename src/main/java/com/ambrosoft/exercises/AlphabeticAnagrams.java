package com.ambrosoft.exercises;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 12/19/17
 * <p>
 * https://www.codewars.com/kata/alphabetic-anagrams
 * <p>
 * If we create all anagrams of input word and sort them alphabetically, what is the rank of that input word
 * Brute force: generate all anagrams, sort them, REMOVE DUPS! find on the sorted list
 * <p>
 * First anagram: input letters sorted, last: the same but reverse
 * Related question: how many anagrams are there without duplicates
 * <p>
 * We can't afford to generate and sort... but we can treat the letters as kind-of digits
 * <p>
 * For a word like QUESTION, we can ask how many words starting with letters smaller than Q
 * Corollary: we are not interested in all the words after QUESTION
 * <p>
 * Rank will be the sum of number of anagrams
 * starting with all the letters smaller than Q (for each one we take it out from the collection)
 * + number of words starting with Q followed by letter smaller than U (so one Q is taken out)
 * + starting with QU followed by smaller than E (no such words)
 * + starting with QUE followed by smaller than S
 * <p>
 * There's problem with duplicates
 */
public class AlphabeticAnagrams {
    private static final BigInteger TWO = BigInteger.valueOf(2);

    private static BigInteger factorial(final int n) {
        switch (n) {
            case 1:
                return BigInteger.ONE;
            case 2:
                return TWO;
            default:
                return factAux(n, BigInteger.ONE);
        }
    }

    private static BigInteger factAux(final int n, final BigInteger acc) {
        return n == 1 ? acc : factAux(n - 1, acc.multiply(BigInteger.valueOf(n)));
    }

    private static BigInteger countPermutationsWithoutDups(final Map<Character, Integer> counts) {
        int letterCount = 0;
        for (Integer count : counts.values()) {
            letterCount += count;
        }
        BigInteger total = factorial(letterCount);  // bug was here! counts.size()!!!
        for (final Integer count : counts.values()) {
            if (count > 1) {
                total = total.divide(factorial(count));
            }
        }
        return total;
    }

    private static TreeMap<Character, Integer> subtractChar(Map<Character, Integer> counts, Character character) {
        final TreeMap<Character, Integer> result = new TreeMap<>(counts);
        final Integer count = result.get(character);
        if (count == null) {
            throw new IllegalStateException();
        } else if (count == 1) {
            result.remove(character);
        } else {
            result.put(character, count - 1);
        }
        return result;
    }

    // given a bag of letters with counts and a letter
    // count all words starting with letters smaller than given letter
    private static BigInteger countAnagramsBeforeLetter(TreeMap<Character, Integer> charCounts, Character limit) {
        BigInteger sum = BigInteger.ZERO;
        for (Character starting : charCounts.keySet()) {
            if (starting < limit) {
                sum = sum.add(countPermutationsWithoutDups(subtractChar(charCounts, starting)));
            } else {
                break;
            }
        }
        return sum;
    }

    public BigInteger listPosition(final String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        } else if (word.length() < 2) {
            return BigInteger.ONE;
        } else {
            final char[] chars = word.toCharArray();

            final TreeMap<Character, Integer> charCounts = new TreeMap<>();
            for (Character c : chars) {
                charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
            }

            BigInteger sum = BigInteger.ONE;
            // how many anagrams starting with letters smaller than input's first letter
            sum = sum.add(countAnagramsBeforeLetter(charCounts, chars[0]));
            // next: how many starting with FIRST, but 2nd smaller than pattern's 2nd, etc.
            TreeMap<Character, Integer> smaller = new TreeMap<>(charCounts);
            for (int i = 0; i < chars.length - 1; i++) {
                smaller = subtractChar(smaller, chars[i]);
                sum = sum.add(countAnagramsBeforeLetter(smaller, chars[i + 1]));
            }
            return sum;
        }
    }

    static void test(String word) {
        AlphabeticAnagrams aa = new AlphabeticAnagrams();
        BigInteger rank = aa.listPosition(word);
        System.out.println(String.format("%s %s", word, rank.toString()));
    }

    public static void main(String[] args) {
        test("QUESTION");
        test("AAAB");
        test("ABAB");
        test("BAAA");
        test("");
        test("BOOKKEEPER");
//        BigInteger fact10 = factorial(6);
//        System.out.println("fact10 = " + fact10);
    }
}
