package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created on 12/17/17
 * <p>
 * CodeWars
 * <p>
 * Erol's idea was to at first disregard colors, only pay attention to same-as-neighbors
 * <p>
 * One can fill the 2D table of distances a la DP
 * <p>
 * - everything on the border gets a 1 (1 step to escape)
 * - every cell with a neighbor of different color gets a 1 (and so does the neighbor)
 * - if all neighbors have the same color, then our color is 1+ the MIN of distances
 * <p>
 * But there is a trick: some neighbor distances are not YET known; if we assign 5, and later discover neighbor 3
 * then the 5 should actually be 4
 * <p>
 * If neighbors same color then dist is at least 2
 * <p>
 * Do we need to update once assigned distances?
 * Can we find an order of assignment that will be able to assign just once?
 * <p>
 * 1s are constant, their neighbors with same colored neighbors are 2s and also constant
 * we can think about not yet assigned cells w/ same colored neighbors
 * Those bordering on 2s are 2 or 3
 * Is it multiple shortest paths in a graph?
 */
public class CentreOfAttention {
    static class Image {
        int width;
        int height;
        int[] pixels;
    }

    static class Central_Pixels_Finder extends Image {
        Stats stats = null;

        int pixel(int row, int col) {
            return pixels[row * width + col];
        }

        boolean neighboursSameColor(int row, int col) {
            final int color = pixel(row, col);
            return pixel(row - 1, col) == color &&
                    pixel(row + 1, col) == color &&
                    pixel(row, col - 1) == color &&
                    pixel(row, col + 1) == color;
        }

        void computeDist(int[][] dist, int row, int col) {
            dist[row][col] =
                    neighboursSameColor(row, col) ?
                            1 + Math.min(
                                    Math.min(dist[row - 1][col], dist[row + 1][col]),
                                    Math.min(dist[row][col - 1], dist[row][col + 1]))
                            : 1;
        }

        void findDistances() {
            final int height = this.height;
            final int width = this.width;
            final int[][] dist = new int[height][width];

            final int maxVal = height * width;
            for (int[] row : dist) {
                Arrays.fill(row, maxVal);
            }

            Arrays.fill(dist[0], 1);
            Arrays.fill(dist[height - 1], 1);
            for (int row = 1; row < height; row++) {
                dist[row][0] = dist[row][width - 1] = 1;
            }

            for (int layer = 1; layer < Math.min(width, height) / 2 + 1; layer++) {
                for (int col = layer; col < width - layer; ++col) {
                    computeDist(dist, layer, col);
                    computeDist(dist, height - layer - 1, col);
                }
                for (int row = layer; row < height - layer; ++row) {
                    computeDist(dist, row, layer);
                    computeDist(dist, row, width - layer - 1);
                }
            }

            Stats stats = new Stats();
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    stats.add(pixel(row, col), dist[row][col], row * width + col);
                }
            }

            for (int[] row : dist) {
                System.out.println(Arrays.toString(row));
            }

            this.stats = stats;
        }

        void showPixels() {
            System.out.println(String.format("w=%d h=%d", width, height));
            final int[] row = new int[width];
            for (int j = 0; j < height; ++j) {
                System.arraycopy(pixels, j * width, row, 0, width);
                System.out.println(Arrays.toString(row));
            }
        }

        public int[] central_pixels(int colour) {
            if (stats == null) {
                showPixels();
                findDistances();
            }
            return stats.getPoints(colour);
        }
    }

    static class Stats {
        final HashMap<Integer, Integer> maxPerColor = new HashMap<>();
        final HashMap<Integer, ArrayList<Integer>> maxPointsPerColor = new HashMap<>();

        void add(Integer color, Integer dist, Integer point) {
            final Integer maxSoFar = maxPerColor.get(color);
            if (maxSoFar == null || dist > maxSoFar) {
                maxPerColor.put(color, dist);
                final ArrayList<Integer> points = new ArrayList<>(2);
                points.add(point);
                maxPointsPerColor.put(color, points);
            } else if (dist.equals(maxSoFar)) {
                maxPointsPerColor.get(color).add(point);
            }
        }

        int[] getPoints(int color) {
            final ArrayList<Integer> points = maxPointsPerColor.get(color);
            if (points != null) {
                final int size = points.size();
                final int[] result = new int[size];
                for (int i = size; --i >= 0; ) {
                    result[i] = points.get(i);
                }
                return result;
            } else {
                return new int[0];
            }
        }
    }

    static void test1() {
        Central_Pixels_Finder im = new Central_Pixels_Finder();
        im.width = 10;
        im.height = 6;
        im.pixels = new int[]{
                1, 1, 4, 4, 4, 4, 2, 2, 2, 2,
                1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                1, 1, 1, 1, 1, 3, 2, 2, 2, 2,
                1, 1, 1, 1, 1, 3, 3, 3, 2, 2,
                1, 1, 1, 1, 1, 1, 3, 3, 3, 3};

        System.out.println(Arrays.toString(im.central_pixels(1)));
        System.out.println(Arrays.toString(im.central_pixels(2)));

    }

    static void test2() {
        Central_Pixels_Finder im = new Central_Pixels_Finder();
        im.width = 2;
        im.height = 2;
        im.pixels = new int[]{
                8, 7,
                7, 8
        };

        System.out.println(Arrays.toString(im.central_pixels(8)));
        System.out.println(Arrays.toString(im.central_pixels(7)));
    }

    static void test_square_10() {
        Central_Pixels_Finder im = new Central_Pixels_Finder();
        im.width = 10;
        im.height = 10;
        im.pixels = new int[100];

        System.out.println(Arrays.toString(im.central_pixels(0)));
    }

    static void test_square_9() {
        Central_Pixels_Finder im = new Central_Pixels_Finder();
        im.width = 9;
        im.height = 11;
        im.pixels = new int[9 * 11];

        System.out.println(Arrays.toString(im.central_pixels(0)));
    }

    public static void main(String[] args) {
        test_square_9();
    }
}
