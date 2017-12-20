package com.ambrosoft.exercises;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by jacek on 11/11/17.
 * <p>
 * https://www.hackerrank.com/challenges/equal/problem
 * <p>
 * action can add 1,2,5 to all except one
 * goal: equal counts
 * observation: penultimate state: all equal except one higher by 1,2, or 5
 * <p>
 * search space is pretty large: any non-recipient can be chosen and any amount
 * is it similar to edit distance?
 * path is a sequence of actions: (index, increment)
 * we may think of increment = 0 for the chosen non-recipient
 * final state = initial + incr1 + ... + incr_n
 * all states should be equal in the end
 * <p>
 * final_n = initial_n + k*1 + l*2 + m*5 + n*0
 * <p>
 * k + l + m + n = N, and only N 0s total
 * <p>
 * Maybe some similarity to dispensing change using coins 1,2,5 but the target is not known
 * <p>
 * An idea: target can be known if we concentrate on reducing differences, target is (0,0,0)
 * We can also think of the elements as a set, order doesn't matter as long as diffs go to 0
 * operator application is adding 1|2|5 to n-1 elements, then subtracting minimum and sorting to normalize
 * Penultimate state is (0,0,...,0,n)
 * Such states can be thought as nodes in a graph, then we search for the shortest path
 * Alternative view, going backwards, we start from (0,0,0) and try to reach initial differences
 * One can imagine filling a DP-table from ground up until initial differences are reached
 * Or is it similar to edit distance?
 * Do we see overlapping problems?
 * <p>
 * 2,2,3,7 (0,0,1,5) -> 3,3,3,8 (0,0,0,5) -> 8,8,8,8 (0,0,0,0)
 * <p>
 * skipping 3rd and adding ones has the effect of subtracting 1 from 3rd difference
 * <p>
 * experiment: skip 1st
 * <p>
 * 2,2,3,7 -> 2,3,4,8 (0,1,2,6)
 * <p>
 * experiment: skip last, add 2
 * <p>
 * 2,2,3,7 -> 4,4,5,7 (0,0,1,3) effect: subtraction of minimum + delta (2)
 * <p>
 * skip last, add 5
 * <p>
 * 7,7,10,7 -> 0,0,3,0  reduced to 0 nicely, remaining 3 can be reduced in 2 steps (not one like the fastest)
 * <p>
 * It doesn't make sense to skip positions with difference of 0; that would mean adding the same amount to other surpluses
 * increasing inequality
 *
 * The greedy version appears to work OK with HackerRank testing but is deemed too slow
 * perhaps array allocation and sorting are avoidable
 */
public class ChocolateEquality {

    static void equalize(int[] counts) {
        int[] state = normalize(counts);
        int counter = 0;
        int toSkip;
        while ((toSkip = findFirstNonZero(state)) >= 0) {
            int val = state[toSkip];
            int toAdd = val >= 5 ? 5 : val >= 2 ? 2 : 1;
            state = step(state, toSkip, toAdd);
            ++counter;
        }
        System.out.println("counter = " + counter);
    }

    static int[] step(int[] counts, int skipThisIndex, int add) {
        // first add to all
        for (int i = counts.length; --i >= 0; ) {
            counts[i] += add;
        }
        // subtract back from item to be skipped
        counts[skipThisIndex] -= add;
        return normalize(counts);
    }

    static int findFirstNonZero(int[] normalized) {
        for (int i = 0; i < normalized.length; i++) {
            if (normalized[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    static int[] normalize(final int[] counts) {
        Arrays.sort(counts);
        final int min = counts[0];
        for (int i = counts.length; --i >= 0; ) {
            counts[i] -= min;
        }
        int nonZero = findFirstNonZero(counts);
//        System.out.println("nonZero = " + nonZero);
        if (nonZero > 0) {
            int[] result = new int[counts.length - nonZero + 1];
            System.arraycopy(counts, nonZero - 1, result, 0, result.length);
//            System.out.println(Arrays.toString(result));
            return result;
        }
//        System.out.println(Arrays.toString(counts));
        return counts;
    }

    public static void main(String[] args) throws FileNotFoundException {
        equalize(new int[]{2, 2, 3, 7});
        equalize(new int[]{53, 361, 188, 665, 786, 898, 447, 562, 272, 123, 229, 629, 670, 848, 994, 54, 822, 46, 208, 17, 449, 302, 466, 832, 931, 778, 156, 39, 31, 777, 749, 436, 138, 289, 453, 276, 539, 901, 839, 811, 24, 420, 440, 46, 269, 786, 101, 443, 832, 661, 460, 281, 964, 278, 465, 247, 408, 622, 638, 440, 751, 739, 876, 889, 380, 330, 517, 919, 583, 356, 83, 959, 129, 875, 5, 750, 662, 106, 193, 494, 120, 653, 128, 84, 283, 593, 683, 44, 567, 321, 484, 318, 412, 712, 559, 792, 394, 77, 711, 977, 785, 146, 936, 914, 22, 942, 664, 36, 400, 857});
        equalize(new int[]{761, 706, 697, 212, 97, 845, 151, 637, 102, 165, 200, 34, 912, 445, 435, 53, 12, 255, 111, 565, 816, 632, 534, 617, 18, 786, 790, 802, 253, 502, 602, 15, 208, 651, 227, 305, 848, 730, 294, 303, 895, 846, 337, 159, 291, 125, 565, 655, 380, 28, 221, 549, 13, 107, 166, 31, 245, 308, 185, 498, 810, 139, 865, 370, 790, 444, 27, 639, 174, 321, 294, 421, 168, 631, 933, 811, 756, 498, 467, 137, 878, 40, 686, 891, 499, 204, 274, 744, 512, 460, 242, 674, 599, 108, 396, 742, 552, 423, 733, 79, 96, 27, 852, 264, 658, 785, 76, 415, 635, 895, 904, 514, 935, 942, 757, 434, 498, 32, 178, 10, 844, 772, 36, 795, 880, 432, 537, 785, 855, 270, 864, 951, 649, 716, 568, 308, 854, 996, 75, 489, 891, 331, 355, 178, 273, 113, 612, 771, 497, 142, 133, 341, 914, 521, 488, 147, 953, 26, 284, 160, 648, 500, 463, 298, 568, 31, 958, 422, 379, 385, 264, 622, 716, 619, 800, 341, 732, 764, 464, 581, 258, 949, 922, 173, 470, 411, 672, 423, 789, 956, 583, 789, 808, 46, 439, 376, 430, 749, 151});
        equalize(new int[]{134, 415, 784, 202, 34, 584, 543, 119, 701, 7, 700, 959, 956, 975, 484, 426, 738, 508, 201, 527, 816, 136, 668, 624, 535, 108, 1, 965, 857, 152, 478, 344, 567, 262, 546, 953, 199, 90, 72, 900, 449, 773, 211, 758, 100, 696, 536, 838, 204, 738, 717, 21, 874, 385, 997, 761, 845, 998, 78, 703, 502, 557, 47, 421, 819, 945, 375, 370, 35, 799, 622, 837, 924, 834, 595, 24, 882, 483, 862, 438, 221, 931, 811, 448, 317, 809, 561, 162, 159, 640, 217, 662, 197, 616, 435, 368, 562, 162, 739, 949, 962, 713, 786, 238, 899, 733, 263, 781, 217, 477, 220, 790, 409, 383, 590, 726, 192, 152, 240, 352, 792, 458, 366, 341, 74, 801, 709, 988, 964, 800, 938, 278, 514, 76, 516, 413, 810, 131, 547, 379, 609, 119, 169, 370, 502, 112, 448, 695, 264, 688, 399, 408, 498, 765, 749, 925, 918, 458, 913, 234, 611});
        equalize(new int[]{512, 125, 928, 381, 890, 90, 512, 789, 469, 473, 908, 990, 195, 763, 102, 643, 458, 366, 684, 857, 126, 534, 974, 875, 459, 892, 686, 373, 127, 297, 576, 991, 774, 856, 372, 664, 946, 237, 806, 767, 62, 714, 758, 258, 477, 860, 253, 287, 579, 289, 496});
        equalize(new int[]{520, 862, 10, 956, 498, 956, 991, 542, 523, 664, 378, 194, 76, 90, 753, 868, 837, 830, 932, 814, 616, 78, 103, 882, 452, 397, 899, 488, 149, 108, 723, 22, 323, 733, 330, 821, 41, 322, 715, 917, 986, 93, 111, 63, 535, 864, 931, 372, 47, 215, 539, 15, 294, 642, 897, 98, 391, 796, 939, 540, 257, 662, 562, 580, 747, 893, 401, 789, 215, 468, 58, 553, 561, 169, 616, 448, 385, 900, 173, 432, 115, 712});
        Scanner in = new Scanner(new FileInputStream("/Users/jacek/Downloads/input03.txt"));
        int T = in.nextInt();
        for (int t = 0; t < T; ++t) {
            int N = in.nextInt();
            int[] counts = new int[N];
            for (int i = 0; i < N; ++i) {
                counts[i] = in.nextInt();
            }
            equalize(counts);
        }
    }
}
