package com.ambrosoft.exercises;

import java.io.IOException;
import java.util.*;

/**
 * Created by jacek on 1/18/17.
 * <p>
 * From Google interview on the same date
 */
public class I18N {

    public static final int MINLEN = 4;

    static String shorten(String word) {
        int len = word.length();
        if (len < MINLEN) {
            return word;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(word.charAt(0)).append(len - 2).append(word.charAt(len - 1));
            return sb.toString();
        }
    }

    static List<String> shortenAll(final String word) {
        final int len = word.length();
        if (len >= MINLEN) {
            final List<String> result = new ArrayList<>();
            final StringBuilder sb = new StringBuilder();
            sb.append(word.charAt(0));
            for (int n = len - 2; n > 1; --n) {
                sb.append(n).append(word.substring(n + 1));
                result.add(sb.toString());
                sb.setLength(1);
            }
            return result;
        }
        return Collections.emptyList();
    }

    static void testShorten(String word) {
        String res = shorten(word);
        System.out.println(String.format("%s -> %s", word, res));
    }

    static void testShortenAll(String word) {
        List<String> res = shortenAll(word);
        System.out.println(String.format("%s -> %s", word, res));
    }

    static Set<String> nonUniqAbbrev(final Set<String> dict) {
        final Set<String> seen = new HashSet<>();
        final Set<String> result = new HashSet<>();

        for (final String word : dict) {
            final String abbrev = shorten(word);
            if (seen.contains(abbrev)) {
                result.add(abbrev);
            } else {
                seen.add(abbrev);
            }
        }
        return result;
    }

    static Set<String> nonUniqAbbrevAll(final Set<String> dict) {
        final Set<String> seen = new HashSet<>();
        final Set<String> result = new HashSet<>();

        for (final String word : dict) {
            final List<String> abbrevs = shortenAll(word);
            for (String abbrev : abbrevs) {
                if (seen.contains(abbrev)) {
                    result.add(abbrev);
                } else {
                    seen.add(abbrev);
                }
            }
        }
        return result;
    }

    static boolean isUnique(String word, Set<String> bad) {
        final String abbrev = shorten(word);
        return !bad.contains(abbrev);
    }

    static String shortestUnique(String word, Set<String> bad) {
        final List<String> abbrevs = shortenAll(word);
        for (String abbrev : abbrevs) {
            if (!bad.contains(abbrev)) {
                return abbrev;
            }
        }
        return word;
    }

    static void testUnique() throws IOException {
        final Set<String> dict = new HashSet<>();
        WordTransformer.readDict(dict);
        System.out.println("dict = " + dict.size());

        final Set<String> nua = nonUniqAbbrev(dict);
        System.out.println("nua = " + nua.size());

        for (String word : Arrays.asList("google", "giggle", "square", "cat", "gimbal", "gilravagers")) {
            System.out.println(String.format("%s -> %b", word, isUnique(word, nua)));
        }
    }

    static void testUniqueAll() throws IOException {
        final Set<String> dict = new HashSet<>();
        WordTransformer.readDict(dict);
        System.out.println("dict = " + dict.size());

        final Set<String> nua = nonUniqAbbrevAll(dict);
        System.out.println("nua = " + nua.size());

        for (String word : Arrays.asList("google", "giggle", "square", "cat", "gimbal", "gilravagers")) {
            System.out.println(String.format("%s -> %s", word, shortestUnique(word, nua)));
        }
    }

    /*
        find shortest unique abbreviation
        of course the full form is always unique
        when we look at dictionary words, we generate all abbreviations
        at this point all are candidates
        when later for another word we see abbrevs already seen we remember them as bad abbrevs
        question remains: how to remember shortest unique
        maybe by caching
     */

    public static void main(String[] args) throws IOException {
        testShorten("google");
        testShortenAll("google");
        testShorten("giggle");

        testUnique();
        testUniqueAll();
    }
}
