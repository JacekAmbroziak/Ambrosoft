package com.ambrosoft.exercises;

import java.util.Arrays;

import static java.lang.Integer.min;
import static java.util.stream.IntStream.range;

/**
 * Created on 12/9/17
 * <p>
 * CodeWars
 * <p>
 * Create a "snake" of 1's not touching itself, longest
 * Approach:
 * Start upper/left, continue as long as possible, turn right, continue
 * stop if can't continue after turning
 * can't continue in current direction if cell exists but has a neighboring 1 in some direction
 * other than from where we come
 * This approach constructs the snake by growing it w/i constraints
 * <p>
 * Another approach would supply whole segments to cover corners of ever smaller rectangles
 * This would reuse a pattern that we see is at work rather than emerge the snake
 * <p>
 * The organic approach has the benefit that it can work with matrices with some prefilled 1's
 * <p>
 * Can't pass all the tests on CodeWars, faulty tests
 */
public class Spiralizor {
    public static String toString(final int[][] cells) {
        final StringBuilder sb = new StringBuilder();
        for (int[] row : cells) {
            sb.append(Arrays.toString(row)).append('\n');
        }
        return sb.toString();
    }

    enum Direction {
        RIGHT(1, 0),
        LEFT(-1, 0),
        UP(0, -1),
        DOWN(0, 1);

        final int deltaCol;
        final int deltaRow;

        Direction(int deltaCol, int deltaRow) {
            this.deltaCol = deltaCol;
            this.deltaRow = deltaRow;
        }

        Direction turnRight() {
            switch (this) {
                case RIGHT:
                    return DOWN;
                case LEFT:
                    return UP;
                case UP:
                    return RIGHT;
                case DOWN:
                    return LEFT;
                default:
                    throw new IllegalArgumentException();
            }
        }

        Direction turnLeft() {
            switch (this) {
                case RIGHT:
                    return UP;
                case LEFT:
                    return DOWN;
                case UP:
                    return LEFT;
                case DOWN:
                    return RIGHT;
                default:
                    throw new IllegalArgumentException();
            }
        }

        int nextRow(int row) {
            return row + deltaRow;
        }

        int nextCol(int col) {
            return col + deltaCol;
        }
    }

    private static int[][] createSquareMatrix(final int size) {
        final int[][] a = new int[size][];
        for (int i = size; --i >= 0; ) {
            a[i] = new int[size];
        }
        return a;
    }

    private static boolean touching(int[][] a, int row, int col) {
        final int size = a.length;  // assuming square a
        if (row < 0 || col < 0 || row == size || col == size) {
            return false;
        } else {
            return a[row][col] == 1;
        }
    }

    private static boolean touching(int[][] a, int row, int col, Direction dir) {
        return touchingInDirection(a, row, col, dir) ||
                touchingInDirection(a, row, col, dir.turnRight()) ||
                touchingInDirection(a, row, col, dir.turnLeft());
    }

    private static boolean touchingInDirection(int[][] a, int row, int col, Direction dir) {
        return touching(a, dir.nextRow(row), dir.nextCol(col));
    }

    private static boolean tryStep(int[][] a, int row, int col, Direction dir) {
        final int size = a.length;  // assuming square a
        if (row < 0 || col < 0 || row == size || col == size) {
            return false;
        } else if (a[row][col] == 1) {
            return false;
        } else if (touching(a, row, col, dir)) {
            return false;
        } else {
            a[row][col] = 1;
            return true;
        }
    }

    public static int[][] spiralize(final int size) {
        final int[][] a = createSquareMatrix(size);
        int row = 0, col = 0;
        Direction dir = Direction.RIGHT;
        a[0][0] = 1;
        boolean justTurned = false;
        while (true) {
            final int nextRow = dir.nextRow(row);
            final int nextCol = dir.nextCol(col);
            if (tryStep(a, nextRow, nextCol, dir)) {
                row = nextRow;
                col = nextCol;
                justTurned = false;
            } else if (justTurned) {
                break;
            } else {
                dir = dir.turnRight();
                justTurned = true;
            }
        }
        return a;
    }

    // author's cryptic and buggy "solution"
    private static int[][] spiralize_test(int n) {
        int[][] ary = range(0, n).mapToObj(i -> range(0, n).map(j -> {
            int min = min(min(i, j), min(n - i - 1, n - j - 1));
            return j == min && i == min + 1 ? min % 2 : 1 - min % 2;
        }).toArray()).toArray(int[][]::new);
        if (n % 2 == 0 && n >= 4) {
            ary[n / 2][n / 2 - 1] = 1;
        }
        return ary;
    }

    public static void main(String[] args) {
        final int size = 10;
        int[][] a = Spiralizor.spiralize(size);
        System.out.println(toString(a));
        System.out.println(toString(spiralize_test(size)));
    }
}
