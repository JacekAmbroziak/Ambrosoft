package com.ambrosoft.exercises;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 1/1/18
 * InterviewCake
 * With 2 eggs find the lowest floor such that eggs dropped from the floor breaks
 * 1) sample with 1st egg skipping floors with diminishing skip amounts n, n-1, n-2, etc.
 * 2) after an interval is found, use 2nd egg to sample floors one by one
 */
public class FloorsAndEggs {
    static int nFloors = 100;

    static void sample() {
        final int floorToFind = ThreadLocalRandom.current().nextInt(nFloors);
//        final int floorToFind = 99;
        System.out.println("floorToFind = " + floorToFind);
        // egg #1
        for (int skip = 14, floor = skip; floor < nFloors; --skip, floor += skip) {
            System.out.println("ONE = " + floor);
            if (floor >= floorToFind) { // found upper bound
                int egg2 = floor - skip + 1;    // unskip
                while (egg2 < floorToFind) {
                    System.out.println("TWO = " + egg2);
                    ++egg2;
                }
                System.out.println("found = " + egg2);
                return;
            }
        }
    }

    public static void main(String[] args) {
        sample();
    }
}
