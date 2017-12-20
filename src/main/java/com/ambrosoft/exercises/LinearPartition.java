package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/19/16.
 */
public class LinearPartition {
    /*
        how to partition an int array into k segments (w/o rearranging)
        to minimize the maximum sum among them
        ideally one would want equal valued partitions but that might not be possible
        minimizing the maximum value is the best approximation of making them similar

        It was not obvious to me, how to decompose the problem into subproblems for the DP approach
        Turns out (hint from Skiena) one can consider the last partition and optimal solution to preceding k-1
        in a way similar to DP Coin Change
        we can pick admissible positions for the last index
            this will determine 1) the final sum, 2) the subproblem of optimally partitioning all the preceding elements
            we are interested in the bigger of the two values
            and minimizing that winner across all options for last index
     */

    private static int sum(int[] a, int start, int end) {
        int result = 0;
        while (start < end) {
            result += a[start++];
        }
        return result;
    }

    // determine best partitioning of a[0..end) into k partitions
    static int recursiveMaxSum(final int[] a, final int k, final int end, int[][] memo, int[][] indexes) {
//        System.out.println(String.format("k %d end %d -> ?", k, end));
        if (memo[k][end] > 0) {
            return memo[k][end];
        } else {
            if (k == 1) {   // base case: one partition -> all elements
                return memo[k][end] = sum(a, 0, end);
            } else {
                // where the final division can be?
                // k partitions: k-1 indexes
                // assuming minimal partitions are of size 1, the start of last partition is >= k-1 & < end

                int minMaxSum = Integer.MAX_VALUE;
                // try out all possible values of index starting the last partition
                for (int lastStart = k - 1; lastStart < end; ++lastStart) {
                    final int subproblemSolution = recursiveMaxSum(a, k - 1, lastStart, memo, indexes);
                    final int lastPartitionSum = sum(a, lastStart, end);
                    final int bigger = Math.max(subproblemSolution, lastPartitionSum);
                    if (bigger < minMaxSum) {
                        minMaxSum = bigger;
                        indexes[k][end] = lastStart;
                    }
                }
//                System.out.println(String.format("k %d end %d -> %d", k, end, minMaxSum));
                return memo[k][end] = minMaxSum;
            }
        }
    }

    private static int nonNegativeElements(int[][] memo) {
        int count = 0;
        for (int[] ints : memo) {
            for (int val : ints) {
                if (val >= 0) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static void partition(int[] a, int k) {
        System.out.println(Arrays.toString(a));
        final int length = a.length;
        final int[][] indexes = new int[k + 1][length + 1];

        final int[][] memo = new int[k + 1][length + 1];
        for (int i = 0; i < k; i++) {
            Arrays.fill(memo[i], -1);
        }

        int result = recursiveMaxSum(a, k, length, memo, indexes);
        System.out.println("result = " + result);
        System.out.println("sum(a,0,a.length) = " + sum(a, 0, length));
//        System.out.println(Arrays.toString(indexes));
        System.out.println("nonNegativeElements(MEMO) = " + nonNegativeElements(memo));
        printPartition(a, a.length, k, indexes);
    }

    private static void printPartition(int[] a, int length, int k, int[][] indexes) {
        if (length > 0) {
            int index = indexes[k][length];
            int sum = sum(a, index, length);
            System.out.println(String.format("index %d sum %d", index, sum));
            printPartition(a, index, k - 1, indexes);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
//        int[] a = Utils.createRandomArray(20, 100);
        partition(a, 5);
    }
}
