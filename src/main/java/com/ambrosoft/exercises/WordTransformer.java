package com.ambrosoft.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacek on 11/9/16.
 */
public class WordTransformer {
    /*
        first word, final word and all the intermediate need to be in dictionary

        approach?
     */

    static void readDict(Set<String> words) throws IOException {
        final URL pwordlistURL = WordTransformer.class.getResource("/wordlist.txt");
        final BufferedReader in = new BufferedReader(new InputStreamReader(pwordlistURL.openStream()));
        String line;
        while ((line = in.readLine()) != null) {
            words.add(line.trim());
        }
        in.close();
    }

    static void transform(String first, String last, Set<String> dict) {
        final int len = first.length();
        if (len == last.length()) {
            if (dict.contains(first) && dict.contains(last)) {
                if (first.equals(last)) {
                    System.out.println("words are same");
                } else {
                    System.out.println("first = " + first);
                    System.out.println("last = " + last);
                }
            } else {
                throw new IllegalArgumentException("not in dict");
            }
        } else {
            throw new IllegalArgumentException("diff len");
        }
    }

    public static void main(String[] args) throws IOException {
        final Set<String> words = new HashSet<>();
        readDict(words);
        System.out.println("words = " + words.size());
        System.out.println("words.contains(\"lamp\") = " + words.contains("lamp"));
        System.out.println("words.contains(\"damp\") = " + words.contains("damp"));
        transform("damp", "like", words);
    }
}
