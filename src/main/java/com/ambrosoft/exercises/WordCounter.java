package com.ambrosoft.exercises;

import com.google.common.collect.Comparators;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Jacek R. Ambroziak
 */
final class WordCounter {
    private final HashMap<String, Counter> counterHashMap = new HashMap<>(4096);
    private long cumulativeMillis = 0L;
    private int cumulativeMergeCount = 0;

    /**
     * like Integer but mutable to support efficient incrementation
     */
    private static final class Counter implements Comparable<Counter> {
        private int value;

        Counter(final int value) {
            this.value = value;
        }

        void increment() {
            ++value;
        }

        void incrementBy(final Counter other) {
            value += other.value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }

        int getValue() {
            return value;
        }

        Integer toInteger() {
            return value;
        }

        @Override
        public int compareTo(final Counter o) {
            return Integer.compare(value, o.value);
        }
    }

    void countWord(final String word) {
        final Counter counter = counterHashMap.get(word);
        if (counter != null) {
            counter.increment();
        } else {
            counterHashMap.put(word, new Counter(1));
        }
    }

    /**
     * Exportable, immutable state of a word counter
     */
    static final class WordCount implements Comparable<WordCount> {
        static final Comparator<WordCount> COMPARATOR = new WordCountComparator();

        final String word;
        final int count;

        private WordCount(final String word, final int count) {
            this.word = word;
            this.count = count;
        }

        static WordCount fromEntry(final Map.Entry<String, Counter> entry) {
            return new WordCount(entry.getKey(), entry.getValue().getValue());
        }

        @Override
        public String toString() {
            return String.format("\"%s\":%d", word, count);
        }

        @Override
        public int compareTo(final WordCount o) {
            return count - o.count;
        }

        private static class WordCountComparator implements Comparator<WordCount> {
            @Override
            public int compare(final WordCount o1, final WordCount o2) {
                return o1.compareTo(o2);
            }
        }
    }


    /**
     * Modifies this object by adding another set of counts
     *
     * @param other counts to be added to this
     */
    WordCounter mergeIn(final WordCounter other) {
        final Instant before = Instant.now();
        final HashMap<String, Counter> counts = this.counterHashMap;    // store in local variable
        for (final Map.Entry<String, Counter> entry : other.counterHashMap.entrySet()) {
            final String key = entry.getKey();
            final Counter counter = counts.get(key);
            if (counter != null) {
                counter.incrementBy(entry.getValue());
            } else {
                counts.put(key, entry.getValue());
            }
        }
        // gather performance data
        cumulativeMergeCount += other.cumulativeMergeCount + 1;
        cumulativeMillis += other.cumulativeMillis + Duration.between(before, Instant.now()).toMillis();
        return this;
    }

    int size() {
        return counterHashMap.size();
    }

    Set<String> getAllWords() {
        return counterHashMap.keySet();
    }

    Optional<Integer> getCount(final String word) {
        final Counter counter = counterHashMap.get(word);
        return counter != null ? Optional.of(counter.toInteger()) : Optional.empty();
    }

    /**
     * This function is rather expensive to run; it is intended to run once
     * An alternative implementation would continually update eg. a heap of counters
     * to make more frequent sampling of current N best 
     *
     * @param k nonnegative number of words of highest frequency in order of nonincreasing frequency
     * @return List of immutable WordCounts of highest frequency in order of nonincreasing frequency
     */
    List<WordCount> topWords(final int k) {
        checkArgument(k >= 0, "Argument was %s but expected nonnegative", k);
        return getWordCountStream().collect(Comparators.greatest(k, WordCount.COMPARATOR));
    }

    public Stream<WordCount> getWordCountStream() {
        return counterHashMap.entrySet().stream().map(WordCount::fromEntry);
    }

    String getPerformanceDataAsString() {
        return String.format("%d merges in %d milliseconds, (%.2f msec/merge)", cumulativeMergeCount, cumulativeMillis, (double) cumulativeMillis / cumulativeMergeCount);
    }

    public static void main(String[] args) {
        WordCounter wc1 = new WordCounter();
        WordCounter wc2 = new WordCounter();

        wc1.countWord("monday");
        wc1.countWord("tuesday");
        wc2.countWord("tuesday");
        wc2.countWord("wednesday");

        wc1.mergeIn(wc2);

        System.out.println("wc1 = " + wc1.counterHashMap);
        System.out.println("wc1 = " + wc1.topWords(0));
        System.out.println("wc1 = " + wc1.topWords(20));
    }
}
