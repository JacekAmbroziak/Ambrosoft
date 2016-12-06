package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by jacek on 12/3/16.
 */
public class ContinuousMedian {
    /*
        we want to know the median value of a stream of integers updated as the integers are seen
        precise understanding of "median"
        1) undefined for empty set
        2) same element for singleton
        3) mean for 2 elements

        we want to keep the 2 heaps balanced
     */


    // store upper half of elements in min queue, tracking the smallest of upper half
    final PriorityQueue<Integer> bigger = new PriorityQueue<>();
    // store bottom half of elements in max queue, tracking the biggest among them
    final PriorityQueue<Integer> smaller = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });

    int add(final int val) {
        smaller.add(val);   // add to smaller but then enforce invariant
        final int biggerSize = bigger.size();
        if (smaller.size() > biggerSize + 1 || biggerSize > 0 && smaller.peek() > bigger.peek()) {
            bigger.add(smaller.poll());
        }

        while (bigger.size() > smaller.size() + 1) {
            smaller.add(bigger.poll());
        }
        return median();
    }

    int median() {
        final int biggerSize = bigger.size();
        final int smallerSize = smaller.size();

        if (biggerSize == smallerSize) {
            if (biggerSize == 0) {
                throw new IllegalStateException("empty set");
            } else {
                return (bigger.peek() + smaller.peek()) / 2;
            }
        } else if (smallerSize > biggerSize) {
            return smaller.peek();
        } else {
            return bigger.peek();
        }
    }

    public static void main(String[] args) {
        final ContinuousMedian cm = new ContinuousMedian();
        final int[] a = Utils.createRandomArray(100, 100);
        System.out.println(Arrays.toString(a));
        for (int i : a) {
            System.out.println("i = " + i);
            int result = cm.add(i);
            System.out.println("result = " + result);
        }
        System.out.println("cm = " + cm);
    }
}
