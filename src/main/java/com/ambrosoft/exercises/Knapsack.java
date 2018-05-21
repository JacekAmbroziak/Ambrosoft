package com.ambrosoft.exercises;

/**
 * Created on 12/31/17
 *
 * Excellent example of DP bottom-up
 */
public class Knapsack {
    static class Obj {
        int value;
        int weight;
    }

    static int findMaxValue(final int fullCapacity, final Obj[] objects) {
        final int[] maxForCapacity = new int[fullCapacity + 1];
        // compute for all capacities starting from 1 all the way to full capacity
        for (int capacity = 1; capacity < fullCapacity; capacity++) {
            int currentMax = 0;
            // try fitting every object
            for (final Obj obj : objects) {
                if (obj.weight <= capacity) {
                    // good thing we already know max for remaining capacity :-)
                    // (this is the most beautiful line: trade capacity for value, use prev. computed max for remaining)
                    final int maxUsingObj = obj.value + maxForCapacity[capacity - obj.weight];
                    if (maxUsingObj > currentMax) {
                        currentMax = maxUsingObj;
                    }
                }
            }
            maxForCapacity[capacity] = currentMax;
        }
        return maxForCapacity[fullCapacity];
    }

}
