package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacek on 11/13/16.
 */

public class ShortestSupersequence {

    /*
        find shortest subarray containing all elements

        similar to our full-text search algorithm
            generator heap

        probably O(mn): need to look at every element & see if one of sought
            but elts sought can be hashed
            still all elements need to be seen (except the lucky case when all are found as prefix)
        worst case: shortest array is the whole array

        entirely possible that no matching subarray exists
            special case: looking for 1 element

        brute force: all subarrays, check if contain elts
            faster brute force not all subarrays, only those that start & end with set element

        As in previous problems, we can have best so far, and current candidate

        Observation: the matching subarray needs to first and last w/ one of elements

        An approach: delete all elements not equal to sought

        D&C: matching subarray on the left, or right, or crossing: lots of work, not sure of O()
     */

    static class Interval {
        static final Interval MAX_INTERVAL = new Interval(0, Integer.MAX_VALUE - 1);
        final int first;    // inclusive
        final int last;      // inclusive

        Interval(int first, int last) {
            this.first = first;
            this.last = last;
        }

        int length() {
            return last - first + 1;
        }

        @Override
        public String toString() {
            return "[" + first + ", " + last + ']';
        }
    }

    static void checkArgs(int[] elements, int[] a) {
        if (elements == null || elements.length == 0 || a == null || a.length == 0) {
            throw new IllegalArgumentException("arrays need to be non-empty");
        }
        if (elements.length > a.length) {
            throw new IllegalArgumentException("pattern array too long");
        }
    }

    private static boolean match(int[] a, int first, int last, Set<Integer> set) {
        final HashSet<Integer> clone = new HashSet<>(set);
        for (int i = first; i <= last; i++) {
            if (clone.remove(a[i]) && clone.isEmpty()) {    // element existed && becomes empty
                return true;
            }
        }
        return false;
    }

    private static Set<Integer> toSet(int[] elements) {
        final HashSet<Integer> set = new HashSet<>();
        final int elementCount = elements.length;
        for (int i = elementCount; --i >= 0; ) {
            set.add(elements[i]);
        }
        return set;
    }

    static Interval bruteForce(int[] elements, int[] a) {
        Interval result = Interval.MAX_INTERVAL;

        final Set<Integer> set = toSet(elements);
        final int elementCount = elements.length;
        final int len = a.length;
        for (int i = 0; i < len; i++) {
            if (set.contains(a[i])) {   // valid interval first
                for (int j = i + elementCount - 1; j < len; j++) {
                    if (set.contains(a[j])) {   // valid interval last
                        // check if [i,j] covers all elements
                        if (match(a, i, j, set) && j - i + 1 < result.length()) {
                            result = new Interval(i, j);    // better found
                        }
                    }
                }
            }
        }
        return result;
    }

    static Interval find(int[] elements, int[] a) {
        checkArgs(elements, a);
        int len = a.length;
        return bruteForce(elements, a);
    }

    static void test(int[] elements, int[] a) {
        System.out.println(Arrays.toString(elements));
        System.out.println(Arrays.toString(a));
        Interval result = find(elements, a);
        System.out.println("result = " + result);
    }

    public static void main(String[] args) {
        int[] elements = {1, 5, 9};
        int[] a = {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};
        test(elements, a);
    }
}
