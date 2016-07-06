package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created by jacek on 7/1/16.
 */
public class WordString {

    public static void main(String[] args) {
        Set<String> dict = new HashSet<>(Arrays.asList("bra", "ab"));
        List<Match> bra = matches("abracadabra", "bra");
        List<Match> ab = matches("abracadabra", "ab");
        System.out.println();
        List<Match> all = matches("abracadabra", dict);
        System.out.println();

        split("fastman", new HashSet<>(Arrays.asList("man", "fast")));

        List<LinkedList<String>> result = solutions("makota",
                new HashSet<>(Arrays.asList("ala", "ma", "kota", "mak", "o", "ta")));
        System.out.println();
    }


    static List<LinkedList<String>> solutions(String input, Set<String> dict) {
        System.out.println("input = " + input);
        final List<LinkedList<String>> result = new ArrayList<>();
        if (input.isEmpty()) {
            result.add(new LinkedList<>());
        } else {
            for (int i = 1; i <= input.length(); ++i) {
                final String word = input.substring(0, i);
                if (dict.contains(word)) {
                    for (LinkedList<String> solution : solutions(input.substring(i), dict)) {
                        solution.addFirst(word);
                        result.add(solution);
                    }
                }
            }
        }
        System.out.println(input + "\tresult = " + result.size());
        return result;
    }


    static List<String> split(String input, Set<String> dict) {

        List<Match> all = matches(input, dict);

        Match[] array = new Match[all.size()];
        array = all.toArray(array);

        Arrays.sort(array);

        // a solution is a seq of indexes of Matches that cover input


        return null;

    }

    void search(Match[] matches, ArrayList<Integer> indexes, int start) {


    }

    static List<Match> matches(String input, String word) {
        int start = input.indexOf(word);
        if (start >= 0) {
            ArrayList<Match> matches = new ArrayList<>();
            do {
                int end = start + word.length();
                matches.add(new Match(word, start, end));
                start = input.indexOf(word, end);
            } while (start > 0);

            return matches;
        } else {
            return Collections.emptyList();
        }
    }

    static List<Match> matches(String input, Set<String> dict) {
        ArrayList<Match> matches = new ArrayList<>();
        for (String word : dict) {
            matches.addAll(matches(input, word));
        }
        return matches;
    }

    static class Match implements Comparable<Match> {
        final String word;
        final int start;
        final int end;

        Match(String word, int start, int end) {
            this.word = word;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Match o) {
            int diff = start - o.start;
            return diff != 0 ? diff : end - o.end;
        }

        @Override
        public String toString() {
            return "[" + word + " @ " + start + ':' + end + ']';
        }
    }


}
