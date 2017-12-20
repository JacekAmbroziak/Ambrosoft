package com.ambrosoft.exercises;

import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;


/**
 * Created on 12/5/17
 * <p>
 * you are given a two dimensional array that contains mostly zeros, but some ones, and a starting point.
 * Your objective is to fill in the area that is surrounded by 1's that contain the starting point.
 * <p>
 * We need representation of the array
 * <p>
 * Propagation from starting point: can add a 1 next to starting point if not already a 1
 * This is a new starting point
 */
public class FillArea {

    static class Area {
        final int nRows;
        final int nCols;
        final int[][] cells;

        Area(int nRows, int nCols) {
            cells = new int[this.nRows = nRows][this.nCols = nCols];
        }

        Area(int[][] cells) {
            this.cells = copy(cells);
            nRows = cells.length;
            nCols = cells[0].length;
            reverseRows(this.cells);
        }

        private static int[][] copy(final int[][] data) {
            final int length = data.length;
            final int[][] result = new int[length][];
            for (int i = length; --i >= 0; ) {
                result[i] = data[i].clone();
            }
            return result;
        }

        static int[][] reverseRows(int[][] cells) {
            int i = 0, j = cells.length - 1;
            while (i < j) {
                final int[] temp = cells[i];
                cells[i++] = cells[j];
                cells[j--] = temp;
            }
            return cells;
        }

        int[][] reverseRows() {
            return reverseRows(cells);
        }

        Point left(final int row, final int col) {
            return new Point(row, col > 0 ? col - 1 : nCols - 1);
        }

        Point right(final int row, final int col) {
            int ncol = col + 1;
            return new Point(row, ncol == nCols ? 0 : ncol);
        }

        Point up(final int row, final int col) {
            int nrow = row + 1;
            return new Point(nrow == nRows ? 0 : nrow, col);
        }

        Point down(final int row, final int col) {
            return new Point(row > 0 ? row - 1 : nRows - 1, col);
        }


        static Area fromBytes(byte[] rows) {
            Area result = new Area(rows.length, 8);
            for (int i = 0; i < rows.length; i++) {
                result.cells[i] = rowFromByte(rows[i]);
            }
            return result;
        }

        static int[] rowFromByte(byte b) {
            int[] row = new int[8];
            int mask = 0x80;
            for (int i = 0; i < 8; ++i) {
                row[i] = (byte) ((b & (mask >> i)) == 0 ? 0 : 1);
            }
            return row;
        }

        @Override
        public String toString() {
            return toString(cells);
        }

        public static String toString(final int[][] cells) {
            final StringBuilder sb = new StringBuilder();
            for (int[] row : cells) {
                sb.append(Arrays.toString(row)).append('\n');
            }
            return sb.toString();
        }

        boolean equals(Area other) {
            if (other != null && other.nRows == nRows && other.nCols == nCols) {
                for (int row = nRows; --row >= 0; ) {
                    if (!Arrays.equals(other.cells[row], cells[row])) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }

        boolean contains(int row, int col) {
            return row >= 0 && col >= 0 && row < nRows && col < nCols;
        }

        boolean fillIfZero(final int row, final int col) {
            if (contains(row, col) && cells[row][col] == 0) {
                cells[row][col] = 1;
                return true;
            } else {
                return false;
            }
        }
    }

    public int[][] fillArea(final int[][] input, Point start) {
        final Area area = new Area(input);
        final Deque<Point> q = new LinkedList<>();
        q.add(new Point(start));
        while (q.size() > 0) {
            final Point p = q.removeLast();
            final int row = p.x, col = p.y;
            if (area.fillIfZero(row, col)) {
                q.add(area.left(row, col));
                q.add(area.up(row, col));
                q.add(area.right(row, col));
                q.add(area.down(row, col));
            }
        }
        return area.reverseRows();
    }

    @Test
    public void testFillSmall() {
        FillArea fill = new FillArea();
        int[][] example = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}};


        System.out.println(Area.toString(example));

        Point point = new Point(2, 2);
        int[][] solution = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}};
        final int[][] result = fill.fillArea(example, point);

        System.out.println(Area.toString(result));

        assertEquals(solution, result);
        System.out.println("done");
    }

    @Test
    public void testFillLarge() {
        FillArea fill = new FillArea();
        int[][] example = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        System.out.println(Area.toString(example));


        Point point = new Point(2, 2);
        int[][] solution = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        final int[][] result = fill.fillArea(example, point);
        System.out.println(Area.toString(result));

        assertEquals(solution, result);
        System.out.println("done");
    }

    @Test
    public void testFillIncompleteLoop() {
        FillArea fill = new FillArea();
        int[][] example = new int[][]{
                {0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0}};
        System.out.println(Area.toString(example));

        Point point = new Point(6, 4);
        int[][] solution = new int[][]{
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1}};
        final int[][] result = fill.fillArea(example, point);
        System.out.println(Area.toString(result));

        assertEquals(solution, result);
    }

    public static void main(String[] args) {

        FillArea fa = new FillArea();

        fa.testFillSmall();
        fa.testFillLarge();
        fa.testFillIncompleteLoop();

//        Area area = new Area(10, 10);
//        System.out.println(area);
//        Area filled = fill(area, 5, 5);
//        System.out.println(area);

        byte[] input = new byte[]{
                0b00000000,
                0b00011110,
                0b00010010,
                0b00010010,
                0b01110010,
                0b01000010,
                0b01111110,
                0b00000000
        };

        byte[] result = new byte[]{
                0b00000000,
                0b00011110,
                0b00011110,
                0b00011110,
                0b01111110,
                0b01111110,
                0b01111110,
                0b00000000
        };

//        Area area1 = Area.fromBytes(input);
//        System.out.println(area1);
//
//        Area filled1 = fill(area1, 5, 3);
//        System.out.println(filled1);
//
//        boolean test = area1.equals(filled1);
//        System.out.println("test = " + test);
//
//        testResult(input, 5, 3, result);

    }
}
