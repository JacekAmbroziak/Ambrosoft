package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 7/5/16.
 */
public class RobotInAGrid {
    /*
        the robot has to walk from (0,0) corner down-right to diagonal corner, avoiding obstacles
        Approach: DP table

        It would also be possible to reason backwards (sub-goal generation): to reach C_end, we need first
        to reach C_end_a or C_end_b -- and so on. Some subgoals will be encountered many times.

        One can see the grid as a a graph too.
     */


    interface Obstacles {
        boolean isObstacle(int x, int y);
    }

    static class Obstacles1 implements Obstacles {
        private static Obstacles INSTANCE = new Obstacles1();

        private Obstacles1() {
        }

        static Obstacles getInstance() {
            return INSTANCE;
        }

        @Override
        public boolean isObstacle(int row, int column) {
//            return (row + column) % 6 == 0;
            return row == column;
        }
    }

    static class Grid {
        private final int rows;
        private final int columns;
        private final Obstacles obstacles;

        Grid(int rows, int columns, Obstacles obstacles) {
            this.rows = rows;
            this.columns = columns;
            this.obstacles = obstacles;
        }

        int getRows() {
            return rows;
        }

        int getColumns() {
            return columns;
        }

        boolean isObstacle(int r, int c) {
            return obstacles.isObstacle(r, c);
        }
    }

    static void solve(Grid grid) {
        // DP table
        final int nRows = grid.getRows();
        final int nColumns = grid.getColumns();
        final int[][] dist = new int[nRows + 1][nColumns + 1];
        // MAXINT everywhere to signify unexplored or unreachable
        for (int[] ints : dist) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        dist[0][0] = 0; // starting point
        // initialize 1st row
        final int[] row1 = dist[0];
        for (int c = 1; c < nColumns; ++c) {
            if (grid.isObstacle(0, c)) {
                break;  // this cell and the rest will keep infinity
            } else {
                row1[c] = row1[c - 1] + 1;
            }
        }
        // initialize 1st column
        for (int r = 1; r < nRows; ++r) {
            if (grid.isObstacle(r, 0)) {
                break;  // this cell and the rest will keep infinity
            } else {
                dist[r][0] = dist[r - 1][0] + 1;
            }
        }

        for (int row = 1; row < nRows; row++) {
            for (int col = 1; col < nColumns; col++) {
                if (grid.isObstacle(row, col)) {
                    // do nothing
                } else {
                    final int prev = Math.min(dist[row - 1][col], dist[row][col - 1]);
                    if (prev != Integer.MAX_VALUE) {
                        dist[row][col] = prev + 1;
                    }
                }
            }
        }
        System.out.println("dist[] = " + dist[nRows - 1][nColumns - 1]);
    }

    public static void main(String[] args) {
        Grid grid = new Grid(10, 10, Obstacles1.getInstance());
        solve(grid);
    }
}
