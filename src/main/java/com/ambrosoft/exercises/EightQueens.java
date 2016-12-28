package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/26/16.
 */
public class EightQueens {
    static final int N = 8;
    /*
        Based on hints...
        Systematically explore the search space, row by row, eg. starting from lowest
        No constraints in the lowest row
        Going upwards, more and more positions will be under attack
        A predicate is needed to check if placement possible
        Data representation: full grid not needed! Just an array, indexed by row, giving chosen column in that row
        Each recursion level can attempt to place a queen in its row
     */

    static void solve(int[] column, int row) {
        if (row >= 0) {
            for (int col = 0; col < N; col++) {
                if (available(column, row, col)) {
                    column[row] = col;
                    solve(column, row - 1);
                }
            }
        } else {
            System.out.println(Arrays.toString(column));
            print(column);
        }
    }

    private static boolean available(final int[] column, final int row, final int col) {
        for (int r = row + 1, delta = 1; r < N; ++r, ++delta) {
            if (col == column[r]) { // is the proposed column already taken
                return false;
            }
            if (col - delta >= 0 && column[r] == col - delta) {
                return false;
            }
            if (col + delta < N && column[r] == col + delta) {
                return false;
            }
        }
        return true;
    }

    static void print(final int[] column) {
        final String queen = "X";
        final String empty = "+";

        for (int r = 0; r < N; r++) {
            final int col = column[r];
            for (int c = 0; c < col; c++) {
                System.out.print(empty);
            }
            System.out.print(queen);
            for (int c = col + 1; c < N; c++) {
                System.out.print(empty);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        solve(new int[N], N - 1);
    }
}
