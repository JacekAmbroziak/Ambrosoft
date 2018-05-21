package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/11/17.
 * <p>
 * Important: avoid double counting
 * <p>
 * Approach:
 * divide subproblems into 2 sets
 * 1) using the last/biggest coin
 * 2) only using remaining coins
 */
public class CoinWays {
    static int[] COINS = {1, 2, 5, 10, 20, 50};

    /*
        recursive
        combinatorial explosion
        educational to imagine tree of calls as a data-structure tree
     */
    static int countWays(final int amount, final int[] coins, final int coinCount) {
        if (amount == 0) {
            return 1;
        } else if (coinCount == 0) {  // no more coins
            return 0;
        } else {
            final int lastCoin = coins[coinCount - 1];
            int countWithLastCoinUsed = 0;
            // count all possibilities that use the last coin
            // depending on the value of the last coin and remaining amount, the last coin may be used (subtracted) 0 or more times
            for (int remaining = amount; remaining >= lastCoin; ) {
                countWithLastCoinUsed += countWays(remaining -= lastCoin, coins, coinCount - 1);
            }
            // add all possibilities that don't use the last coin
            return countWithLastCoinUsed + countWays(amount, coins, coinCount - 1);
        }
    }

    /*
        recursive with memoization
        may still lead to SO for large inputs
     */
    static long countWaysMemo(final int amount, final int[] coins, final int coinCount, final long[][] memo) {
        long result = memo[amount][coinCount];
        if (result >= 0L) { // has it been set
            return result;
        } else {
            final int lastCoin = coins[coinCount - 1];
            long count = 0;
            // count all possibilities that use the last coin
            // depending on the value of the last coin and remaining amount, the last coin may be used (subtracted) 0 or more times
            for (int remaining = amount; remaining >= lastCoin; ) {
                count += countWaysMemo(remaining -= lastCoin, coins, coinCount - 1, memo);
            }
            // add all possibilities that don't use the last coin
            return memo[amount][coinCount] = count + countWaysMemo(amount, coins, coinCount - 1, memo);
        }
    }

    static void test(int amount, int[] coins) {
        int result = countWays(amount, coins, coins.length);
        System.out.println(String.format("%d %s -> %d", amount, Arrays.toString(coins), result));
    }

    static void testMemo(final int amount, final int[] coins) {
        final int coinCount = coins.length;
        final long[][] memo = new long[amount + 1][coinCount + 1];
        for (int i = 0; i <= amount; i++) {
            Arrays.fill(memo[i], -1L);
            memo[i][0] = 0L;
        }
        for (int i = 0; i <= coinCount; i++) {
            memo[0][i] = 1L; // important: one way to give change for amount==0
        }
        long result = countWaysMemo(amount, coins, coinCount, memo);
        System.out.println(String.format("%d %s -> %d", amount, Arrays.toString(coins), result));
        System.out.println("bottom up " + changePossibilitiesBottomUp_InterviewCake(amount, coins));
    }

    /*
        surprisingly simple solution from Interview Cake
        I expected a 2D array indexed by amounts and "up to n" coins (prefix)
     */
    static int changePossibilitiesBottomUp_InterviewCake(final int amount, final int[] denominations) {
        final int[] waysOfDoingNCents = new int[amount + 1];  // array of zeros from 0..amount
        waysOfDoingNCents[0] = 1;

        for (final int coin : denominations) {    // each coin is considered only once!
            // scan all amounts between coin and amount ([coin..amount])
            // number of ways for each is increased by count of ways with that coin used
            for (int val = coin; val <= amount; val++) {
                waysOfDoingNCents[val] += waysOfDoingNCents[val - coin];
            }
        }
        /*
            If there was 1 coin only, a single pass would populate values at [coin..amount]
            For one coin the number of ways will always be either 1 or 0 for impossible
            The single pass sets each count at val only once using previously set or default count(val-coin)
            ALL counts are built up from that initial 1 at the head of the array
            Adding another coin to consideration will potentially increase counts thanks to *access* to other combinations
            The line in the loop is executed once per every combination of some amount and some coin
            It says, the count needs to be increased with all the ways we can change val-coin
            (the coin gives us access to count[val-coin]; that count already encompasses previous "accesses" val-coin-coin etc.)

            Each coin can only add new ways, hence the +=
            After each execution of the important line counts(val) is set to its final value given coins seen so far

            The last value to be set is the sought result waysOfDoingNCents[amount]
            The last increment to that value is full count for value amount-coin
         */

        return waysOfDoingNCents[amount];
    }

    public static void main(String[] args) {
//        System.out.println("result = " + countWays(77, COINS, 5));
//        test(4, new int[]{1, 2, 3});
//        test(10, new int[]{2, 3, 5, 6});
        testMemo(1777, new int[]{1, 2, 5, 10, 20, 50});
        testMemo(1777, new int[]{1, 3, 5});
    }
}
