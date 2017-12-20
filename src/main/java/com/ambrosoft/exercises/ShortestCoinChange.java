package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/12/17.
 * <p>
 * MEMO table can also be built bottom up
 */
public class ShortestCoinChange {
    static int[] COINS = {1, 2, 5, 10, 20, 50};

    static int[] MEMO = new int[1000];

    static {
        Arrays.fill(MEMO, -1);  // "not yet set"
        MEMO[0] = 0;    // base case: zero coins to change 0
        for (int coin : COINS) {    // base cases for amounts equal to coin values
            MEMO[coin] = 1;
        }
    }

    static int shortestChange(final int amount) {
        if (MEMO[amount] >= 0) {    // has it been set?
            return MEMO[amount];    // use it
        } else {
            int minRemainderChange = Integer.MAX_VALUE;
            // consider starting with any admissible coin to find the shortest change for possible remainders
            for (int i = COINS.length; --i >= 0; ) {
                final int coin = COINS[i];
                if (coin < amount) {    // equality covered in MEMO
                    final int remainderChange = shortestChange(amount - coin);
                    if (remainderChange < minRemainderChange) {
                        minRemainderChange = remainderChange;
                    }
                }
            }
            return MEMO[amount] = minRemainderChange + 1;  // min is shortest change for remainder, 1 is for the last coin
        }
    }

    static void test(int amount) {
        final int shortestChange = shortestChange(amount);
        System.out.println(String.format("%d -> %d", amount, shortestChange));
    }

    public static void main(String[] args) {
        test(77);
    }
}
