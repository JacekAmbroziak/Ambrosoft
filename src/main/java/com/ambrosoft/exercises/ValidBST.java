package com.ambrosoft.exercises;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created on 12/29/17
 * <p>
 * InterviewCake
 */
public class ValidBST {
    /*
        Tree is valid when both existing subtrees are valid
        and max value of left <= root <= all vals right
     */


    static int maxVal(BSTNode node) {
        int max = node.value;
        while ((node = node.rgt) != null) {
            max = node.value;
        }
        return max;
    }

    static int minVal(BSTNode node) {
        int min = node.value;
        while ((node = node.lft) != null) {
            min = node.value;
        }
        return min;
    }

    static boolean isValid(final BSTNode node) {
        if (node.lft != null && (!isValid(node.lft) || node.value <= maxVal(node.lft))) {
            return false;
        }
        if (node.rgt != null && (!isValid(node.rgt) || node.value >= minVal(node.rgt))) {
            return false;
        }
        return true;
    }

    // InterviewCake

    /*
        My solution is not even correct :-(  and performs too much tree walking

        Good IC hint: pushing bounds down recursive calls
     */

    static boolean isBinarySearchTree(final BSTNode root) {
        return isBinarySearchTree(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isBinarySearchTree(final BSTNode root, final int lowerBound, final int upperBound) {
        if (root == null) {
            return true;
        } else {
            final int value = root.value;
            if (value <= upperBound) {
                if (value >= lowerBound) {
                    if (isBinarySearchTree(root.lft, lowerBound, value)) {
                        return isBinarySearchTree(root.rgt, value, upperBound);
                    }
                }
            }
            return false;
        }
    }

    // InterviewCake 2
    // Explicit stack uses heap memory not stack

    private static class NodeBounds {
        BSTNode node;
        int lowerBound;
        int upperBound;

        NodeBounds(BSTNode node, int lowerBound, int upperBound) {
            this.node = node;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
    }

    public static boolean isBinarySearchTree_DFS(final BSTNode root) {
        final Deque<NodeBounds> stack = new LinkedList<>();
        stack.push(new NodeBounds(root, Integer.MIN_VALUE, Integer.MAX_VALUE));

        while (!stack.isEmpty()) {
            final NodeBounds nb = stack.pop();
            final BSTNode node = nb.node;
            final int lowerBound = nb.lowerBound;
            final int upperBound = nb.upperBound;
            final int value = node.value;

            // if this node is invalid, we return false right away
            if (value < lowerBound || value > upperBound) {
                return false;
            } else {
                if (node.lft != null) {
                    // this node must be less than the current node
                    stack.push(new NodeBounds(node.lft, lowerBound, value));
                }
                if (node.rgt != null) {
                    // this node must be greater than the current node
                    stack.push(new NodeBounds(node.rgt, value, upperBound));
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int size = 20000;
        int[] a = Utils.createRandomArray(size, size * 10);
        final BSTNode root = BSTNode.populateFromArray(a);

//        System.out.println(BSTNode.printTree(root));
        System.out.println(isValid(root));
        System.out.println(isBinarySearchTree(root));
        System.out.println(isBinarySearchTree_DFS(root));
    }
}
