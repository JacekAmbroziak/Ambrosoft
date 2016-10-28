package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created by jacek on 10/28/16.
 */
public class ChildrenNames {
    // on the surface the task is very simple: some frequency numbers need to be merged and keyed by a representative of synset

    private static void indexSynset(Map<String, TreeSet<String>> synsets, TreeSet<String> synset, String name) {
        synset.add(name);
        synsets.put(name, synset);
    }

    static void addSynset(Map<String, TreeSet<String>> synsets, String name1, String name2) {
        final TreeSet<String> s1 = synsets.get(name1);
        final TreeSet<String> s2 = synsets.get(name2);

        if (s1 != null && s2 != null) {
            if (s1 != s2) { // need to merge
                for (String name : s2) {
                    indexSynset(synsets, s1, name);
                }
            }
        } else if (s1 != null) {
            indexSynset(synsets, s1, name2);
        } else if (s2 != null) {
            indexSynset(synsets, s2, name1);
        } else {
            final TreeSet<String> synset = new TreeSet<>();
            indexSynset(synsets, synset, name1);
            indexSynset(synsets, synset, name2);
        }
    }

    static Collection<TreeSet<String>> makeSynsets(Map<String, String> synonyms) {
        final Map<String, TreeSet<String>> synsets = new HashMap<>();
        for (Map.Entry<String, String> entry : synonyms.entrySet()) {
            addSynset(synsets, entry.getKey(), entry.getValue());
        }
        return synsets.values();
    }

    static Map<String, Integer> totals(Map<String, Integer> freq, Map<String, String> syn) {
        final Map<String, Integer> result = new HashMap<>();

        // syn contains name synonyms, but synsets are implicit
        // one can proceed the Scala way by first building synsets (phase 1) and then summing them up
        // distinct 2 phases
        // not exactly trivial to build synsets: UNION-FIND
        // a tricky example would involve a long loop of synonym "edges"

        final Collection<TreeSet<String>> synsets = makeSynsets(syn);

        for (TreeSet<String> names : synsets) {
            int sum = 0;
            for (String name : names) {
                final Integer count = freq.get(name);
                if (count != null) {
                    sum += count;
                }
            }
            result.put(names.first(), sum);
        }
        return result;
    }

    public static void main(String[] args) {
        final Map<String, Integer> freq = new HashMap<>();
        freq.put("John", 10);
        freq.put("Jon", 3);
        freq.put("Davis", 2);
        freq.put("Kari", 3);
        freq.put("Johnny", 11);
        freq.put("Carlton", 8);
        freq.put("Carleton", 2);
        freq.put("Jonathan", 9);
        freq.put("Carrie", 5);

        final Map<String, String> syn = new HashMap<>();
        syn.put("Jonathan", "John");
        syn.put("Jon", "Johnny");
        syn.put("Johnny", "John");
        syn.put("Kari", "Carrie");
        syn.put("Carleton", "Carlton");

        totals(freq, syn);
    }
}
