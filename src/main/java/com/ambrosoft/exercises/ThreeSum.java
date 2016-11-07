package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jacek on 11/5/16.
 */

public class ThreeSum {
    // Determine if any 3 integers in an array sum to 0

    /*
        analysis: "if any" is important: we can stop at first finding
        brute force: find all 3-tuples: good for checking better solution

        1) sort numbers, take 2 positive - 1 neg, 2 neg - 1 pos
        2) sort numbers, consider each one as target sum for TwoSum problem

        Variation: find sum closest to 0
            this time the Set method doesn't work, maybe Heap
            gainlo explains that the method for closest to zero is THE SAME, except of course accumulating min sum
     */

    // brute force does not count duplicate triples i < j < k
    // tries them all
    static void bruteForce(int[] a) {
        System.out.println("brute force");
        Arrays.sort(a);
        final int len = a.length;
        if (len >= 3) {
            int count = 0;
            for (int i = 0; i < len; i++) {
                final int n1 = a[i];
                for (int j = i + 1; j < len; j++) {
                    final int sum = n1 + a[j];
                    for (int k = j + 1; k < len; k++) {
                        if (sum + a[k] == 0) {
                            System.out.println(String.format("([%d]= %d, [%d]= %d, [%d]= %d)", i, a[i], j, a[j], k, a[k]));
                            ++count;
                        }
                    }
                }
            }
            System.out.println("brute force count = " + count);
        }
    }

    static void squared(int[] a) {
        final int len = a.length;
        if (len >= 3) {
            int count = 0;
            final HashSet<Integer> ints = new HashSet<>();  // will not contain dups!
            for (int i = 0; i < len; i++) {
                ints.add(a[i]);
            }
            for (int i = 0; i < len; i++) {
                final int n1 = a[i];
                for (int j = i + 1; j < len; j++) {
                    if (ints.contains(-(n1 + a[j]))) {
                        ++count;
                    }
                }
            }
//            System.out.println("squared count = " + count);
        }
    }

    static void squaredUsingTwoSum(final int[] a) {
        System.out.println("\nsquared using TwoSum");
        final int len = a.length;
        if (len >= 3) {
            int count = 0;

            Arrays.sort(a);

            for (int k = 0; k < len; k++) { // consider each number in turn
                final int val = -a[k];

                int i = k, j = len - 1;
                while (i < j) {
                    if (i == k) {
                        ++i;
                    } else if (j == k) {
                        --j;
                    } else {
                        final int sum = a[i] + a[j];
                        if (sum < val) {
                            ++i;
                        } else if (sum > val) {
                            --j;
                        } else {
                            ++count;
                            System.out.println(String.format("([%d]= %d, [%d]= %d, [%d]= %d)", i, a[i], j, a[j], k, a[k]));
                            // we can break here if looking for just one solution
                            // moving the pointers (both?) if looking for all solutions
                            ++i;
                            --j;
                        }
                    }
                }
            }
            System.out.println("squared 2-sum count = " + count);
        }
    }

    static void test(int[] a) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        bruteForce(a);
        squared(a);
        squaredUsingTwoSum(a);
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArrayNoDups(100, 200);
//        int[] a = {-12, -11, -10, -8, -6, -1, 11, 12, 15, 19};
        test(a);
//        Arrays.sort(a);
//        test(a);
    }
}
