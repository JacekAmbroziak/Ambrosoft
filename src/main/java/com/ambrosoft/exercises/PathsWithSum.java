package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/12/16.
 */

public class PathsWithSum {

    // looking for all paths where nodes' values sum up to sum
    // values can be negative so we can't prune paths that are too large

    // USE THE FACT that this is a SEARCH tree

    // this function represents ("by definition") brute force
    // it possibly duplicates work & is not using the "search tree" ordering of values

    /*
        How many paths are there in a binary tree?
            We can find a recurrence by considering
            1) number of paths from root to all leaves
            2) all subtrees (of diminishing sizes)

     */

    static int pathsWithSum(final BSTNode node, final int sum) {
        if (node == null) {
            return 0;
        } else {
            final int remaining = sum - node.value;
            int result = 0;
            if (remaining == 0) {   // one path just found but keep looking, values can be negative
                result += 1;
            }
            // paths going left and right, involving parent
            result += pathsWithSum(node.lft, remaining);
            result += pathsWithSum(node.rgt, remaining);

            // paths starting at children
            result += pathsWithSum(node.lft, sum);
            result += pathsWithSum(node.rgt, sum);
            return result;
        }
    }

    static int pathsWithSumSearch(final BSTNode node, final int sum) {
        if (node == null) {
            return 0;
        } else {
            final int remaining = sum - node.value;
            int result = 0;
            if (remaining == 0) {   // one path just found but keep looking, values can be negative
                result += 1;
            }

            // paths going left and right, involving parent
            result += pathsWithSum(node.lft, remaining);

            if (remaining > node.value) {
                result += pathsWithSum(node.rgt, remaining);
            }

            // paths starting at children
            result += pathsWithSum(node.lft, sum);
            result += pathsWithSum(node.rgt, sum);

            return result;
        }
    }

    public static void main(String[] args) {
//        final int[] a = Utils.createRandomArrayNoDups(50, 100);
        final int[] a = {2, 1, 3};
        // [-1, 1, -6, 7, -5]
        final BSTNode root = new BSTNode(a[0]);
        for (int i = 1; i < a.length; ++i) {
            BSTNode.insert(root, a[i]);
        }
        System.out.println(Arrays.toString(a));
        System.out.println(BSTNode.printTree(root));

        System.out.println("result = " + pathsWithSum(root, 3));
        System.out.println("result = " + pathsWithSumSearch(root, 3));
    }
}
