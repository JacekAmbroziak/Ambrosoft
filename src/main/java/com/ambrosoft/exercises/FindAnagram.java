package com.ambrosoft.exercises;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacek on 11/8/16.
 */
public class FindAnagram {
    /*
        An anagram will have the same character count signature as pattern
        one can slide a window of pattern's length and update counters
            if encountering letter from outside pattern, no counter is incremented
            when sliding off a non-pattern char, no modif likewise
            big question: how to discover agreement on counts?

            Simpler perhaps to always update window counts, but still not obvious how to detect agreement
            Agreement: equal counts in test as in pattern
                ideally, simple/fast test for match after slide-update: automaton?
                how many states?
                interpretation of a state: seen XYZ in the last k chars, but explosion of combinations
                    some subset seen: 2^k subsets

        Brute force? generate all anagrams into a Set...
     */

    static HashMap<Character, Integer> signature(String pattern) {
        final HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            final Character key = pattern.charAt(i);
            final Integer count = map.get(key);
            if (count != null) {
                map.put(key, count + 1);
            } else {
                map.put(key, 1);
            }
        }
        return map;
    }

    // create counters for all the pattern's chars
    static HashMap<Character, Integer> match(String pattern) {
        final HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            map.put(pattern.charAt(i), 0);
        }
        return map;
    }

    static void update(Map<Character, Integer> signature, Character oldChar, Character newChar) {
        if (oldChar.equals(newChar)) {
            // do nothing
        } else {
            final Integer count1 = signature.get(oldChar);
            if (count1 != null) {
                signature.put(oldChar, count1 - 1);
            }

            final Integer count2 = signature.get(newChar);
            if (count2 != null) {
                signature.put(newChar, count2 + 1);
            }
        }
    }

    static String findAnagram(String input, String pattern) {
        final int inputLen = input.length();
        final int patLen = pattern.length();
        if (patLen < inputLen) {
            final HashMap<Character, Integer> signature = signature(pattern);
            // initialize test signature
            final HashMap<Character, Integer> test = match(pattern);
            for (int i = 0; i < patLen; i++) {
                update(test, Character.MAX_VALUE, input.charAt(i));
            }

            int start = 0;
            int end = patLen;
            do {
                if (test.equals(signature)) {   // expensive
                    return input.substring(start, start + patLen);
                }
                if (end < inputLen) {
                    update(test, input.charAt(start++), input.charAt(end++));
                } else {
                    return "*not found*";
                }
            } while (true);
        }
        return "*too short*";
    }

    static HashMap<Character, Integer> mapToPrimes(String pattern) {
        final HashMap<Character, Integer> map = new HashMap<>();
        int n = 0;
        for (int i = pattern.length(); --i >= 0; ) {
            final Character key = pattern.charAt(i);
            if (!map.containsKey(key)) {
                map.put(key, Primality.nthPrime(++n));
            }
        }
        return map;
    }

    static long hash(String pattern, Map<Character, Integer> primeCodes) {
        long h = 1L;
        for (int i = 0; i < pattern.length(); i++) {
            h *= primeCodes.get(pattern.charAt(i));
        }
        return h;
    }

    static String findAnagramWithPrimes(String text, String pattern) {
        final HashMap<Character, Integer> primeCodes = mapToPrimes(pattern);
        final long patternCode = hash(pattern, primeCodes);
        final int txtlen = text.length();
        final int patlen = pattern.length();
        final int lastStart = txtlen - patlen;

        if (lastStart > 0) {
            int start = 0;

            LOOP:
            while (start <= lastStart) {
                long testCode = 1L;
                int i = 0;
                // the strategy here is to jump over chars definitely not in the anagram!
                while (i < patlen) {
                    final Integer code = primeCodes.get(text.charAt(start + i));
                    if (code != null) {
                        testCode *= code;
                        ++i;
                    } else {
                        start += i + 1;
                        continue LOOP;
                    }
                }

                if (i == patlen) {
                    if (testCode == patternCode) {
                        return text.substring(start, start + patlen);
                    } else {
                        ++start;
                    }
                }
            }
        }
        return "*too short*";
    }

    static void test(String input, String pattern) {
        System.out.println("input = " + input);
        System.out.println("pattern = " + pattern);
        System.out.println("findAnagram(input,pattern) = " + findAnagram(input, pattern));
        System.out.println("findAnagram(input,pattern) = " + findAnagramWithPrimes(input, pattern));
    }

    public static void main(String[] args) {
        final Map<Character, Integer> map = mapToPrimes("kot");
        final long hash = hash("kot", map);

        test("abahdfgwqgkotagrdd", "kotag");
    }
}
