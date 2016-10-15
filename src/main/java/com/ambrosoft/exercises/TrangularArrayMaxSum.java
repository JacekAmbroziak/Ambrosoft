package com.ambrosoft.exercises;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jacek on 10/8/16.
 * <p>
 * Problem 18
 */

public class TrangularArrayMaxSum {

    static int[][] readData(final BufferedReader bis) throws IOException {
        final ArrayList<int[]> list = new ArrayList<>();
        String line;
        while ((line = bis.readLine()) != null) {
            final String[] numbers = line.split(" ");
            final int[] array = new int[numbers.length];
            for (int i = numbers.length; --i >= 0; ) {
                array[i] = Integer.valueOf(numbers[i]);
            }
            list.add(array);
        }

        final int[][] result = new int[list.size()][];
        list.toArray(result);
        return result;
    }

    static int[][] readData(File file) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final int[][] data = readData(bufferedReader);
        bufferedReader.close();
        return data;
    }

    static int[][] readData(InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final int[][] data = readData(bufferedReader);
        bufferedReader.close();
        return data;
    }

    static InputStream getResourceFile(String name) {
        return TrangularArrayMaxSum.class.getClassLoader().getResourceAsStream(name);
    }

    static int maxPathByEnumeration(final int[][] data, int level, int sum, int index) {
        if (level < data.length) {
            final int down = maxPathByEnumeration(data, level + 1, sum + data[level][index], index);
            final int next = maxPathByEnumeration(data, level + 1, sum + data[level][index + 1], index + 1);
            return Math.max(down, next);
        } else {
            return sum;
        }
    }

    private static int[] bestAtLevel(final int[] prevBest, final int[] row) {
        final int[] best = new int[prevBest.length];    // could be made reusable
        best[0] = prevBest[0] + row[0]; // 1 possibility, no left
        for (int i = 1; i < row.length; i++) {
            // coming from above or from the left
            best[i] = Math.max(prevBest[i] + row[i], prevBest[i - 1] + row[i]);
        }
        return best;
    }

    static int incremental(final int[][] triangle) {
        int[] bestSoFar = new int[triangle[triangle.length - 1].length];
        bestSoFar[0] = triangle[0][0];
        for (int i = 1; i < triangle.length; ++i) {
            bestSoFar = bestAtLevel(bestSoFar, triangle[i]);
        }

        int max = 0;
        for (int i : bestSoFar) {
            max = Math.max(max, i);
        }
        return max;
    }

    public static void main(String[] args) {
        try {
            {
                int[][] data = readData(getResourceFile("small_triangle.txt"));
                System.out.println("result = " + maxPathByEnumeration(data, 1, data[0][0], 0));
                System.out.println("result = " + incremental(data));

            }
            {
                int[][] data = readData(getResourceFile("p067_triangle.txt"));
                System.out.println("result = " + incremental(data));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
