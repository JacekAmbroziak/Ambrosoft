package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 12/24/16.
 */
public class Boxes {
    /*
        One idea from previous implementation in Scala was to order all the boxes by volume
        If we can only stack a strictly smaller box on larger, that smaller box will also have smaller volume:
        any stackable sets will be subsequences of boxes ordered by volume (a total ordering)

        Bug: just comparing on volume and sorting is not enough for deduplication! Equal Boxes may be separated by
        non-equal boxes of the same volume; therefore other dimensions are needed to break the ties
        Deduping can also be done by adding all boxes into a Set (in Scala, case class added equals and hash)

        Or add to TreeSet to both deduplicate and sort.

        A highest stack will be a subsequence of this sorted sequence, subject to the constraint that
        strictly smaller boxes are placed on larger boxes in all dimensions.
        We are trying to maximize the total height of such a subsequence

        One, brute force, option is to try to find all stackable subsequences

        Consider subproblems: searching for highest stack starting from each of the boxes as bottom

        Nature of search expressed as MAX operation over subproblems iff stackable

        maxheight(i) = box[i].h + MAX(j=i+1,n) maxheight(j) iff box[j] < box[i]

        Again...
        Lets consider the sequence of boxes getting smaller.
        Last box is the smallest and constitutes a stack-of-one.
        For every box i we have the maximum height of a stack with that box as a bottom.
        (kind of similar to rod cutting)
        When computing maxheight(j), j<i, we consider *all* previously computed maxheights[j+1..n]
        filtered by stackability of j & i, with interpretation that we can use the stack height
        if indeed that stack can be put on box j

        My thinking was hampered by ideas of potentially multiple stackable subsequences in any prefix/suffix
        But this is actually OK!
        I was also looking for linear solution, rejecting quadratic ideas
        What really matters is that for every box, the max height of the stack with that box as bottom is well defined!
        It really is a function. The stack can be the box by itself, or any subsequence
        Knowing this function (box, maxH(box)) lets me experiment with such pairs as continuations for larger boxes:
        if box1 can be a bottom for box, box1.h + maxH(box) is a candidate for maxH(box1)

        Stick to precise semantics!
        If we tabelarize max heights involving last box as bottom, we still need to search
        for globally max value in the whole table (or we can update the running max)
        but the last value in the table is not automatically the sought max
        It may be the case that the largest block cannot accept some substacks, so it will be counted only as itself
        and some other substack will be taller
     */

    static class Box implements Comparable<Box> {
        final int w;
        final int h;
        final int d;
        final int volume;

        Box(int w, int h, int d) {
            this.w = w;
            this.h = h;
            this.d = d;
            volume = w * h * d;
        }

        int getHeight() {
            return h;
        }

        boolean largerThan(final Box other) {
            return w > other.w && h > other.h && d > other.d;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Box) {
                final Box other = (Box) obj;
                return w == other.w && d == other.d && h == other.h;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return w * 7 + h * 5 + d;
        }

        @Override
        public int compareTo(final Box o) { // total ordering
            int diff = volume - o.volume;
            if (diff != 0) {
                return diff;
            } else if ((diff = h - o.h) != 0) {
                return diff;
            } else if ((diff = w - o.w) != 0) {
                return diff;
            } else {
                return d - o.d;
            }
        }

        @Override
        public String toString() {
            return String.format("[%d, %d, %d]", h, w, d);
        }
    }

    static Box randomBox(int min, int max) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        int w = random.nextInt(min, max);
        int h = random.nextInt(min, max);
        int d = random.nextInt(min, max);
        return new Box(w, h, d);
    }

    static Box[] randomBoxes(int n, int min, int max) {
        final Box[] boxes = new Box[n];
        for (int i = n; --i >= 0; ) {
            boxes[i] = randomBox(min, max);
        }
        return boxes;
    }

    // only when boxes sorted so that equal boxes end up together
    static Box[] removeDups(final Box[] boxes) {
        final int len = boxes.length;
        Box[] temp = new Box[len];
        int index = 1;

        Box current = temp[0] = boxes[0];
        for (int i = 1; i < len; i++) {
            if (boxes[i].equals(current)) {
                // continue
                System.out.println("found i = " + i);
            } else {
                current = temp[index++] = boxes[i];
            }
        }
        return Arrays.copyOf(temp, index);
    }

    /*
        Assuming boxes grow to the right with growing indexes

        maxHeight(i) = box[i].h + MAX(j=i+1,n) maxHeight(j) iff box[j] > box[i]
     */

    static int maxHeight(Box[] boxes, int bottomIndex) {
//        System.out.println("bottomIndex = " + bottomIndex);
        if (bottomIndex == 0) {
            return boxes[0].getHeight();
        } else {
            Box bottom = boxes[bottomIndex];
            int maxHeight = 0;
            for (int i = bottomIndex - 1; i >= 0; --i) {
                final Box next = boxes[i];
                if (bottom.largerThan(next)) {
                    maxHeight = Math.max(maxHeight, maxHeight(boxes, i));
                }
            }
            return bottom.getHeight() + maxHeight;
        }
    }

    /*
        boxes are in sorted order from smallest to largest
     */
    static int maxHeightDP(final Box[] boxes) {
        final int boxCount = boxes.length;
        /*
        classic DP table to be filled from the left w/ solutions to smaller problems: all prefixes of boxes
        value: maxHeight of a stack with that box as bottom (including that bottom)
        Important!  The highest stack may not involve the largest block!
        */
        final int[] maxHeight = new int[boxCount];
        maxHeight[0] = boxes[0].getHeight();  // trivial stack of just the smallest box
        for (int i = 1; i < boxCount; ++i) {  // solve the problem for each prefix
            final Box bottom = boxes[i];
            int max = 0;    // no boxes yet
            for (int j = 0; j < i; ++j) {   // check out all prefix solutions (already computed)
                if (bottom.largerThan(boxes[j])) {    // jth stack can be put on box[i]
                    max = Math.max(max, maxHeight[j]);
                }
            }
            maxHeight[i] = bottom.getHeight() + max;
        }
        final int indexOfMax = findIndexOfMax(maxHeight);
        return maxHeight[indexOfMax];
    }

    private static int findIndexOfMax(final int[] a) {
        final int length = a.length;
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = length; --i >= 0; ) {
            if (a[i] > max) {
                max = a[i];
                index = i;
            }
        }
        return index;
    }

    private static void printSolution(final Box[] boxes, final int[] solution, int bottom) {
        int totalHeight = 0;
        Box prev = null;
        do {
            final Box box = boxes[bottom];
            System.out.println("box = " + box);
            totalHeight += box.getHeight();
            if (prev != null) {
                if (prev.largerThan(box)) {
                    System.out.println("OK");
                } else {
                    System.out.println("NOT OK");
                }
            }
            prev = box;
            bottom = solution[bottom];
        } while (bottom > 0);
        System.out.println("totalHeight = " + totalHeight);
    }

    static int maxHeightDP_sol(final Box[] boxes) {
        final int boxCount = boxes.length;
        // classic DP table to be filled from the left w/ solutions to smaller problems: all prefixes of boxes
        // value: maxHeight of a stack with that box as bottom (including that bottom)
        final int[] maxHeight = new int[boxCount];
        final int[] solution = new int[boxCount];
        maxHeight[0] = boxes[0].getHeight();  // trivial stack of just the smallest box
        solution[0] = -1;
        for (int i = 1; i < boxCount; ++i) {  // solve the problem for each prefix
            final Box bottom = boxes[i];
            int max = 0;    // no boxes yet
            for (int j = 0; j < i; ++j) {   // check out all prefix solutions (already computed)
                if (bottom.largerThan(boxes[j])) {    // can jth stack be put on box[i]?
                    final int maxStack = maxHeight[j];
                    if (maxStack > max) {
                        max = maxStack;
                        solution[i] = j;
                    }
                }
            }
            maxHeight[i] = bottom.getHeight() + max;
        }
        final int indexOfMax = findIndexOfMax(maxHeight);
        printSolution(boxes, solution, indexOfMax);
        return maxHeight[indexOfMax];
    }

    static Box[] removeDupsAndSort(final Box[] boxes) {
        final TreeSet<Box> set = new TreeSet<>();
        for (final Box box : boxes) {
            set.add(box);
        }
        return set.toArray(new Box[set.size()]);    // in sorted order
    }

    public static void main(String[] args) {
        Box[] boxes = randomBoxes(20, 10, 100);
        System.out.println("boxes = " + Arrays.toString(boxes));
//        Arrays.sort(boxes);
//        System.out.println("sorted = " + Arrays.toString(boxes));
//        Box[] deduped = removeDups(boxes);
//        System.out.println("deduped = " + Arrays.toString(deduped));

        Box[] deduped = removeDupsAndSort(boxes);
        System.out.println("deduped = " + Arrays.toString(deduped));


        int max = maxHeight(deduped, deduped.length - 1);
        System.out.println("max = " + max);
        System.out.println("maxHeightDP() = " + maxHeightDP_sol(deduped));
    }
}
