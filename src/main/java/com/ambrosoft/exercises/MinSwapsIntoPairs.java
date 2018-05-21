package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 10/10/16.
 * <p>
 * http://www.geeksforgeeks.org/minimum-number-of-swaps-required-for-arranging-pairs-adjacent-to-each-other
 * <p>
 * Numbers 1 through 2n: n *pairs* but initially not paired
 * "sort" them in minimal node of swaps so that pairs sit together
 * a bit similar to sorting
 * <p>
 * if permutations are nodes in a graph and edges correspond to swaps
 * then the starting position is SOURCE, *many* nodes correspond to final states
 * and a solution is a shortest path from SOURCE to some final state
 * Normal BFS hard because of huge fan-out
 * <p>
 * One approach is divide and conquer similar to finding closest pair in a plane
 * - divide array into 2 'halves' of even sizes
 * - find pairs straddling the division & perform necessary cross-division/long-distance swaps
 * -- after finding a pair we cannot swap yet (they would still be separated!)
 * --- need to find the 2nd long-distance pair (has to exist!)
 * - repeat recursively
 * <p>
 * similarity to finding minimal node of character exchanges to transform strings via DP
 * <p>
 * <p>
 * <p>
 * w/o loss of generality we can assume that 1,2  n,n+1 are paired
 */
class MinSwapsIntoPairs {

    // assuming 1,2  3,4 etc. pairs
    static int pairOf(final int n) {
        return n % 2 == 0 ? n - 1 : n + 1;
    }

    // return node of swaps
    static int orderIntoPairs(final int a[]) {
        final int[] indexOf = new int[a.length + 1];
        for (int i = a.length; --i >= 0; ) {
            indexOf[a[i]] = i;
        }
        return orderIntoPairs(a, indexOf, 0, a.length);
    }

    static int orderIntoPairs(final int[] a, final int[] index, final int from, final int to) {
        final int nPairs = (to - from) / 2;
        if (nPairs == 1) {
            return 0;
        } else {
            final int leftPairCount = nPairs / 2;
            final int split = from + leftPairCount * 2;
            int nSwaps = 0;
            // find long-distance swaps
            for (int i = from; i < split; ++i) {
                final int otherIndex = index[pairOf(a[i])];
                if (otherIndex >= split) {
                    // find another displaced pair (has to exist)
                    for (int j = i + 1; j < split; ++j) {
                        final int otherIndex2 = index[pairOf(a[j])];
                        if (otherIndex2 >= split) {
                            final int temp = a[i];
                            index[a[i] = a[otherIndex2]] = i;
                            index[a[otherIndex2] = temp] = otherIndex2;
                            i = j;
                            ++nSwaps;
                            // forgot to update index at first!
                            break;
                        }
                    }
                }
            }

            return nSwaps + orderIntoPairs(a, index, from, split) + orderIntoPairs(a, index, split, to);
        }
    }


    static void shuffle(final int[] array) {
        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        for (int i = array.length; --i > 0; ) {
            final int index = rand.nextInt(i + 1);
            final int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    static int[] generateExample(final int nPairs) {
        final int[] result = new int[2 * nPairs];
        for (int i = result.length; --i >= 0; ) {
            result[i] = i + 1;
        }
        shuffle(result);
        return result;
    }

    static boolean checkResult(int[] a) {
        for (int i = 0; i < a.length; i += 2) {
            if (a[i + 1] != pairOf(a[i])) {
                return false;
            }
        }

        Arrays.sort(a);

        for (int i = 0; i < a.length; i++) {
            if (a[i] != i + 1) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {

//            int[] example = generateExample(5);
            int[] example = {14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

            shuffle(example);

            System.out.println(Arrays.toString(example));

            int nSwaps = orderIntoPairs(example);
            System.out.println("nSwaps = " + nSwaps);

            System.out.println(Arrays.toString(example));

            final boolean checkResult = checkResult(example);
            if (!checkResult) {
                throw new Error();
            }
            System.out.println("checkResult(example) = " + checkResult);

        }

    }
}
