package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/28/16.
 */
public class PartisanGame {
    /*
        https://community.topcoder.com/stat?c=problem_statement&pm=14426

        looking for a function to fill out a DP table
        we will consider a growing sequence of numbers of stones
        the function should involve the number of stones & who's turn it is
        boolean aliceWins(int n, int[] a, int[] b, boolean aliceTurn)
     */


    // try recursive...
    // works ok but cannot handle large n values
    static boolean aliceWins(int n, int[] a, int[] b) {
        if (n < a[0]) {
            return false;   // Alice looses
        } else {
            for (int aMove : a) {
                if (!bobWins(n - aMove, a, b)) {
                    return true;
                }
            }
            return false;
        }
    }

    static boolean bobWins(int n, int[] a, int[] b) {
        if (n < b[0]) {
            return false;   // Bob looses
        } else {
            for (int bMove : b) {
                if (!aliceWins(n - bMove, a, b)) {
                    return true;
                }
            }
            return false;
        }
    }


    // try recursive, symmetric -- the current player's array of moves goes first
    // BUGGY
    static boolean currentPlayerWins(int n, int[] ourMoves, int[] theirMoves) {
        if (n < ourMoves[0]) {  // we have no move
            return false;
        } else {    // pick some stones
            for (int i = 0; i < ourMoves.length; i++) {
                if (currentPlayerWins(n - ourMoves[i], theirMoves, ourMoves)) {
                    return false;
                }
            }
            return true;
        }
    }

    static boolean DP(final int N, final int[] a, final int[] b) {
        // the DP table
        final boolean[] aliceWins = new boolean[N + 1];

        // solve smaller subproblems first
        // for n from 0 to a[0]-1 Alice looses, don't touch the array
        // then, Alice wins for all subproblems corresponding to her single moves
        // for these subproblems she wins w/ single, final move
        for (int aMove : a) {
            aliceWins[aMove] = true;
        }

        // now, consider problems bigger than Alice's biggest move
        // she can make any move, leaving a number of stones for Bob to work with
        // she can experiment with any move
        // she wins for n, if she can make a move that stops Bob from winning
        for (int n = a[a.length - 1] + 1; n <= N; ++n) {    // growing problem sizes
            // Alice will try to make a move that is guaranteed to win
            for (int i = 0; i < a.length; ++i) {
                final int remaining = n - a[i];
                // now it's Bob's term
                // Bob can try his moves against remaining to maximize his chances
                // can he reduce 'remaining' so Alice loses?

                // a[i] will be a winning move for Alice if Bob cannot win with remaining
                boolean awins = true;
                for (int j = 0; j < b.length; j++) {
                    if (remaining > b[j] && !aliceWins[remaining - b[j]]) {
                        awins = false;
                        break;
                    } else if (remaining == b[j]) {  // Bob wins
                        awins = false;
                        break;
                    }
                }
                if (awins) {    // true value 'survived' Bob's moves
                    aliceWins[n] = true;
                    break;
                }
            }
        }
        return aliceWins[N];
    }

    static String getWinner(int n, int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        return DP(n, a, b) ? "Alice" : "Bob";
    }

    static void test(int n, int[] a, int[] b) {
        String winner = getWinner(n, a, b);
        System.out.println(String.format("%d %s %s %s",
                n, Arrays.toString(a), Arrays.toString(b), winner));
    }

    public static void main(String[] args) {
        test(7, new int[]{3, 4}, new int[]{4});
        test(10, new int[]{1}, new int[]{4, 3, 2});
        test(104982, new int[]{2, 3, 4, 5}, new int[]{2, 5});
        test(999999999, new int[]{4}, new int[]{5});
        test(1000000000, new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});
    }
}
