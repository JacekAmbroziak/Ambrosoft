package com.ambrosoft.exercises;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by jacek on 11/1/17.
 */
public class MatchingWords {
    public static final int ALPHABET_CARDINALITY = 26;
    /*
        pattern: a collection of letters and blanks
        for a word to match each letter needs to have a match in pattern
     */

    static class Pattern {
        final int length;
        int numBlanks = 0;
        int[] counts = new int[ALPHABET_CARDINALITY];

        Pattern(String p) {
            for (int i = p.length(); --i >= 0; ) {
                char c = p.charAt(i);
                if (c == ' ') {
                    ++numBlanks;
                } else {
                    ++counts[c - 'a'];
                }
            }
            length = p.length();
        }

        boolean match(String word) {
            int wlen = word.length();
            if (wlen > length) {
                return false;
            } else {
                int blanks = numBlanks;
                int[] counts = Arrays.copyOf(this.counts, ALPHABET_CARDINALITY);
                for (int i = wlen; --i >= 0; ) {
                    int index = word.charAt(i) - 'a';
                    if (counts[index] > 0) {
                        --counts[index];
                    } else if (blanks > 0) {
                        --blanks;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    static void test(String pattern) throws Exception {
        System.out.println("pattern = " + pattern);
        Pattern p = new Pattern(pattern);
        Scanner scanner = new Scanner(new FileInputStream("/usr/share/dict/words"));
        try {
            String word;
            while ((word = scanner.next()) != null) {
                word = word.toLowerCase();
                if (p.match(word)) {
                    System.out.println(word);
                }
            }
        } catch (NoSuchElementException e) {
            // done reading
        }
    }

    public static void main(String[] args) {
        try {
            test("abracadabra ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
