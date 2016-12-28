package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/24/16.
 */
public class CoinChange {
    /*
        This time looking for ALL ways to make a change
     */

    static int[] COINS = {25, 10, 5, 1};    // sorted descending, unique

    static int countWays(int amount) {
        return countWays(amount, 0);    // start with highest denomination
    }

    /*
        We can imagine a tree with full amount in the root
        1st level children using biggest coin possible
        2nd level: 2nd biggest
        we want to count leaves where amount reaches 0
     */

    static int countWays(final int amount, final int coinIndex) {
        if (amount == 0) {
            return 1;
        } else if (coinIndex == COINS.length) {
            return 0;
        } else {
            final int coin = COINS[coinIndex];
            if (coin > amount) {
                return countWays(amount, coinIndex + 1);
            } else {
                final int maxCoinCount = amount / coin;
                int ways = 0;
                for (int n = maxCoinCount; n >= 0; --n) {
                    final int remaining = amount - n * coin;
                    if (remaining == 0) {
                        ways += 1;
                    } else {
                        ways += countWays(remaining, coinIndex + 1);
                    }
                }
                return ways;
            }
        }
    }

    // this code appears to assume last denomination to be 1
    static int gayle(int amount, int[] denoms, int index) {
        if (index == denoms.length - 1) {
            return 1;   // questionable; seems to assume that last is 1
        } else {
            final int coin = denoms[index];
            int ways = 0;
            for (int n = 0; n * coin <= amount; n++) {
                final int remaining = amount - n * coin;
                ways += gayle(remaining, denoms, index + 1);
            }
            return ways;
        }
    }

    static void test(int amount) {
        int result = countWays(amount);
        System.out.println(String.format("Jacek %d -> %d", amount, result));
        System.out.println(String.format("Gayle %d -> %d", amount, gayle(amount, COINS, 0)));
    }

    public static void main(String[] args) {
        test(6);
    }
}
