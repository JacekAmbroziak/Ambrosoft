package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/11/17.
 *
 * Important: avoid double counting
 *
 * Approach:
 * divide subproblems into 2 sets
 * 1) using the last/biggest coin
 * 2) only using remaining coins
 */
public class CoinWays {
    static int[] COINS = {1, 2, 5, 10, 20, 50};

    static int countWays(final int amount, final int[] coins, final int coinCount) {
        if (amount == 0) {
            return 1;
        } else if (coinCount == 0) {  // no more coins
            return 0;
        } else {
            int count = 0;
            final int lastCoin = coins[coinCount - 1];
            // count all possibilities that use the last coin
            for (int remaining = amount; remaining >= lastCoin; ) {
                count += countWays(remaining -= lastCoin, coins, coinCount - 1);
            }
            // add all possibilities that don't use the last coin
            return count + countWays(amount, coins, coinCount - 1);
        }
    }

    static int countWaysMemo(final int amount, final int[] coins, final int coinCount, final int[][] memo) {
        int result = memo[amount][coinCount];
        if (result >= 0) {
            return result;
        } else {
            int count = 0;
            final int coin = coins[coinCount - 1];
            // count all possibilities that use the last coin
            for (int remaining = amount; remaining >= coin; ) {
                count += countWaysMemo(remaining -= coin, coins, coinCount - 1, memo);
            }
            // add all possibilities that don't use the last coin
            return memo[amount][coinCount] = count + countWaysMemo(amount, coins, coinCount - 1, memo);
        }
    }

    static void test(int amount, int[] coins) {
        int result = countWays(amount, coins, coins.length);
        System.out.println(String.format("%d %s -> %d", amount, Arrays.toString(coins), result));
    }

    static void testMemo(int amount, int[] coins) {
        final int coinCount = coins.length;
        int[][] memo = new int[amount + 1][coinCount + 1];
        for (int i = 0; i <= amount; i++) {
            Arrays.fill(memo[i], -1);
            memo[i][0] = 0;
        }
        for (int i = 0; i <= coinCount; i++) {
            memo[0][i] = 1;
        }
        int result = countWaysMemo(amount, coins, coinCount, memo);
        System.out.println(String.format("%d %s -> %d", amount, Arrays.toString(coins), result));
    }

    public static void main(String[] args) {
//        System.out.println("result = " + countWays(77, COINS, 5));
//        test(4, new int[]{1, 2, 3});
//        test(10, new int[]{2, 3, 5, 6});
        testMemo(77, new int[]{1, 2, 5, 10, 20, 50});
    }
}
