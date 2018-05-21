package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/22/17
 * <p>
 * Given an array,find the maximum j – i such that arr[j] > arr[i]
 * <p>
 * Assuming j>i
 * No answer if all elements equal or descending, otherwise some such pair exists
 * start w/ i,j at ends
 * keep min from the left and max from right and their indices
 * advance i
 * advance --j
 * What about DP or greedy?
 * If we know solution for prefix, how does it change with another element?
 * Or DP:
 * largest distance is max(j-i, dist(i+1,j), dist(i,j-1))
 */
public class LargerFarApart {
    static int dist(int[] a) {
        return dist(a, 0, a.length - 1);
    }

    /*
        This is effectively DFS; too much search
     */
    static int dist(int[] a, int start, int last) {
        System.out.println(String.format("%d %d", start, last));
        if (start == last) {
            return -1;
        } else if (a[start] < a[last]) {
            System.out.println("found " + (last - start));
            return last - start;
        } else {
            return Math.max(dist(a, start + 1, last), dist(a, start, last - 1));
        }
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println(dist(a));
    }

    public static void main(String[] args) {
        test(new int[]{34, 8, 10, 3, 2, 80, 30, 33, 1});
//        test(new int[]{9, 2, 3, 4, 5, 6, 7, 8, 18, 0});
//        test(new int[]{1, 2, 3, 4, 5, 6});
//        test(new int[]{5, 4, 3, 2, 1});
    }
}