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

        What statistics to keep in tree Nodes?
        Can it be full size of the tree?
        Then, when we find a node, we should just return size of left subtree... NO!

        Reset. Say we count all number occurrences seen so far in BST nodes.
        If we want to know the number of ints smaller then val, we could imagine iterating
        all nodes in-order, summing their self counts until reaching val

         Interesting question: how to build an iterator going trough nodes in-order: successor
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
            root = new Node(val, null);
        }
    }

    int getRankOfNumber1(int val) {
        if (root == null) {
            return 0;
        }

        int sum = 0;

        Node node = root.leftmostDescendant();
        sum += node.countSelf;
//        System.out.println("first = " + node);
        while ((node = node.successor()) != null) {
//            System.out.println("succ = " + node);
            if (node.value < val) {
                sum += node.countSelf;
            } else {
                break;
            }
        }

        return sum;
    }

    static void test(RankFromStream ranker, int val) {
        System.out.println(String.format("%d -> %d", val, ranker.getRankOfNumber(val)));
        System.out.println(String.format("%d -> %d", val, ranker.getRankOfNumber1(val)));
    }

    static class Node {
        final int value;
        final Node parent;
        Node lft;
        Node rgt;
        int countSelf;
        int countSmaller;
        int subtreeSize;

        Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
            subtreeSize = 1;
            countSelf = 1;
        }

        void insert(final int val) {
            ++subtreeSize;  // in every case
            if (val == value) {
                ++countSelf;
            } else if (val < value) {
                ++countSmaller;
                if (lft != null) {
                    lft.insert(val);
                } else {
                    lft = new Node(val, this);
                }
            } else {
                if (rgt != null) {
                    rgt.insert(val);
                } else {
                    rgt = new Node(val, this);
                }
            }
        }

        Node leftmostDescendant() {
            return lft == null ? this : lft.leftmostDescendant();
        }

        // return the next Node in-order: with the immediately bigger value
        Node successor() {
            // if right exists, go there and return leftmost descendant
            if (rgt != null) {
                return rgt.leftmostDescendant();
            }
            // if we are a left child, return parent
            if (parent != null && parent.lft == this) {
                return parent;
            }
            // otherwise climb up looking for a right subtree
            Node p = parent, n = this;
            while (p != null && p.rgt == n) {
                n = p;
                p = p.parent;
            }
            if (p != null && p.rgt != null) {
                return p.rgt.leftmostDescendant();
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return String.format("%d (%d)", value, subtreeSize);
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{5, 1, 4, 4, 5, 9, 7, 13, 3};

        RankFromStream obj = new RankFromStream();
        for (int val : data) {
            obj.track(val);
            obj.track1(val);
        }

        test(obj, 1);
        test(obj, 3);
        test(obj, 4);

        Node node = obj.root.leftmostDescendant();
        System.out.println("first = " + node);
        while ((node = node.successor()) != null) {
            System.out.println("succ = " + node);
        }


        System.out.println();

    }
}
