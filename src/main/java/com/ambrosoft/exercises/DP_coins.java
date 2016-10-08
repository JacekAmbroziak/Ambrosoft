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

    static int[] VALUES = {1, 5, 10, 25, 30};

    static int coins(final int target) {
        System.out.println("target = " + target);
        // minimal number of coins to build a sum
        final int[] minCoinCount = new int[target + 1];
        // last coin added to complete the sum
        final int[] lastAddedCoin = new int[target + 1];
        Arrays.fill(minCoinCount, Integer.MAX_VALUE);

        // zero coins builds a sum of zero
        minCoinCount[0] = 0;

        // consider all smaller sums in sequence
        for (int sum = 1; sum <= target; sum++) {
            for (final int value : VALUES) {
                if (value <= sum && minCoinCount[sum - value] + 1 < minCoinCount[sum]) {
                    minCoinCount[sum] = minCoinCount[sum - value] + 1;
                    lastAddedCoin[sum] = value;
                }
            }
        }
        System.out.println("min = " + minCoinCount[target]);

        for (int i = target; i > 0; ) {

            final int val = lastAddedCoin[i];
            System.out.println("added " + val);
            i -= val;

        }
        return minCoinCount[target];
    }

    public static void main(String[] args) {
        System.out.println("coins() = " + coins(57));
    }
}
