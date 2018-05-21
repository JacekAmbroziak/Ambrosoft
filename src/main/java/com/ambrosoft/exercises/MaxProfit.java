package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/20/17
 * <p>
 * https://www.interviewcake.com/
 * <p>
 * Goal: find cheapest preceding largest
 * Approach:
 * Can we solve the problem with prefix of the array, and then see what including the last changes?
 * Example:
 * {10, 7, 5, 8, 11, 9}
 * Solution for prefix {10, 7, 5, 8, 11} is also 6, adding final 9 doesn't change it
 * Solution to {10, 7, 5, 8} is 3, adding 11 improves from 3 to 6 (we can know that if we remember price we sell at/buy at?)
 * {10, 7, 5} profit -2?
 */
public class MaxProfit {

    private static int max(int[] a, int start, int end) {
        int result = a[start];
        while (++start < end) {
            if (a[start] > result) {
                result = a[start];
            }
        }
        return result;
    }

    static int getMaxProfit(final int[] prices) {
        // for every point in time know min so far, and max of remaining, and max profit so far?
        final int len = prices.length;
        int maxProfit = Integer.MIN_VALUE;
        int min = prices[0];
        for (int i = 1; i < len - 1; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
            final int profit = max(prices, i + 1, len) - min;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }
        return maxProfit;
    }

    static int getMaxProfit2(final int[] prices) {
        final int len = prices.length;
        int maxProfit = 0;
        int i = 0, j = len - 1;
        while (i < j) {
            int profit = prices[j] - prices[i];
            if (profit > maxProfit) {
                maxProfit = profit;
            }
//            if ()
        }
        return maxProfit;
    }

    static int getMaxProfit_final(final int[] prices) { // based on hints from InterviewCake
        if (prices == null || prices.length < 2) {
            throw new IllegalArgumentException();
        } else {
            final int len = prices.length;
            int minPriceSoFar = prices[0];
            int maxProfitSoFar = prices[1] - minPriceSoFar;
            for (int i = 1; i < len; i++) {
                final int current = prices[i];
                final int profit = current - minPriceSoFar;
                if (profit > maxProfitSoFar) {
                    maxProfitSoFar = profit;
                }
                if (current < minPriceSoFar) {
                    minPriceSoFar = current;
                }
            }
            return maxProfitSoFar;
        }
    }

    static void test(int[] prices) {
        System.out.println(Arrays.toString(prices));
        System.out.println(getMaxProfit_final(prices));
    }

    public static void main(String[] args) {
        test(new int[]{10, 7, 5, 8, 11, 9});    // buy at 5, sell at 11
        test(new int[]{1, 1, 1, 1});
        test(new int[]{1, 2, 3, 4});
        test(new int[]{4, 3, 2, 1});
        test(new int[]{4, 3, 1, 10, 2, 1});
    }
}
