package com.ambrosoft.exercises;

import java.util.List;

/**
 * User: jacek
 * Date: 6/8/18
 * Time: 11:34 AM
 *
 * @author Jacek R. Ambroziak
 */
final class WhitespaceTokenization {

    private static boolean isWhitespace(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r' || ch == '\f';
    }

    static void countTokens(final List<String> lines, final WordCounters wordCounters) {
        for (final String line : lines) {
            for (int i = line.length(); --i >= 0; ) {
                // search for non whitespace
                if (isWhitespace(line.charAt(i))) {
                    // continue
                } else {
                    final int end = i + 1;
                    while (--i >= 0 && !isWhitespace(line.charAt(i))) {
                        // continue
                    }
                    //                System.out.println("token = >" + token + "<");
                    final String cleaned = Punctuation.stripPunctuation(line.substring(i + 1, end));
                    if (cleaned.length() > 0) {
                        //                    System.out.println("cleaned = >" + cleaned + '<');
                        wordCounters.countWord(cleaned.toLowerCase());
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
//        tokenize("x\tA BB.   ..  DDDDDDD  (ABC)          ,,CCC\n");
//        tokenize("             \t ");
    }

}
