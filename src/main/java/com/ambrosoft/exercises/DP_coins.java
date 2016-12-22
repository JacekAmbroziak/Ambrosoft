package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/5/16.
 * <p>
 * TopCoder
 * <p>
 * Set Min[i] equal to Infinity for all of i
 * Min[0]=0
 * <p>
 * For i = 1 to S
 * For j = 0 to N - 1
 * If (Vj<=i AND Min[i-Vj]+1<Min[i])
 * Then Min[i]=Min[i-Vj]+1
 * <p>
 * Output Min[S]
 */

public class DP_coins {

    static int[] COINS = {1, 5, 10, 25, 30};    // sorted

    static int coins(final int target) {
        System.out.println("target = " + target);
        // minimal number of coins to build a sum
        final int[] minCoinCount = new int[target + 1]; // the "DP table" of subproblems
        // last coin added to complete the sum (data needed for solution reconstruction)
        final int[] lastAddedCoin = new int[target + 1];
        Arrays.fill(minCoinCount, Integer.MAX_VALUE);

        // zero coins builds a sum of zero
        minCoinCount[0] = 0;

        // consider all smaller sums in sequence
        for (int amount = 1; amount <= target; amount++) {
            for (final int coin : COINS) {    // try all admissible coins
                if (coin <= amount) {   // not bigger than amount==problem size
                    // key recurrence: problem reduction by coin value, problem cost increase by 1 coin
                    if (minCoinCount[amount - coin] + 1 < minCoinCount[amount]) {
                        minCoinCount[amount] = minCoinCount[amount - coin] + 1; // found better option
                        lastAddedCoin[amount] = coin;   // note which coin leads to current minimum
                    }
                } else {
                    break;
                }
            }
        }
        System.out.println("min = " + minCoinCount[target]);

        // show the coins
        for (int amount = target; amount > 0; ) {
            final int val = lastAddedCoin[amount];
            System.out.println("added " + val);
            amount -= val;
        }
        return minCoinCount[target];
    }

    public static void main(String[] args) {
        System.out.println("coins() = " + coins(57));
    }
}
