package com.ambrosoft.exercises;

import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * Created by jacek on 1/5/17.
 */
public class RankFromStream {
    /*
        track() needs to update some state, gather all the numbers or their counters
        numbers are not worth storing, but their ordered counters: yes
        Can I have ordered cumulative counters?
        Trouble w/ TreeMap's API: no descending iterator from given value

        With value counters still quite a bit of work to do:
        we don't know the rank immediately unless
        1) we walk all the counters and add them up on query, or
        2) we update all higher counters on 'track'

        Another possibility: cache cumulative counts
        At this time it seems that some linear scan will need to happen
        either on update, or on query

        Gayle's solution: BST with statistics (size of left subtree)
        But Gayle's solution duplicates nodes with same value
     */

    private final TreeMap<Integer, Integer> counters = new TreeMap<>();

    private Node root;

    // fast
    void track(int val) {
        final Integer extant = counters.get(val);
        if (extant != null) {
            counters.put(val, extant + 1);
        } else {
            counters.put(val, 1);
        }
    }

    // slow
    int getRankOfNumber(int val) {
        final NavigableSet<Integer> set = counters.descendingKeySet();
        int rank = 0;
        Integer value = set.floor(val);
        while (value != null) {
            rank += counters.get(value);
            value = set.higher(value);
        }
        return rank - 1;
    }

    void track1(final int val) {
        if (root != null) {
            root.insert(val);
        } else {
            root = new Node(val);
        }
    }

    int getRankOfNumber1(int val) {
        if (root == null) {
            return 0;
        }

        Node node = root;
        int sum = 0;
        if (val == node.value) {
            return node.countSmaller;
        }
        if (val < node.value) {

        }

        return sum;
    }

    static void test(RankFromStream ranker, int val) {
        int rank = ranker.getRankOfNumber(val);
        System.out.println(String.format("%d -> %d", val, rank));
    }

    static class Node {
        final int value;
        Node lft;
        Node rgt;
        int countSelf;
        int countSmaller;

        Node(int value) {
            this.value = value;
        }


        void insert(final int val) {
            if (val == value) {
                ++countSelf;
            } else if (val < value) {
                ++countSmaller;
                if (lft != null) {
                    lft.insert(val);
                } else {
                    lft = new Node(val);
                }
            } else {
                if (rgt != null) {
                    rgt.insert(val);
                } else {
                    rgt = new Node(val);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{5, 1, 4, 4, 5, 9, 7, 13, 3};

        RankFromStream obj = new RankFromStream();
        for (int val : data) {
            obj.track(val);
        }

        test(obj, 1);
        test(obj, 3);
        test(obj, 4);

    }
}
