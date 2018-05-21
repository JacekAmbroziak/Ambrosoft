package com.ambrosoft.exercises;

import java.util.ArrayList;

/**
 * Created on 12/29/17
 * <p>
 * InterviewCake
 */

public class BST2ndLargest {
    /*
        If only root, null
        If degenerate left children only, then 1st left child of root is 2nd largest
        Largest is at the end of the path of right children
        We know that in-order walk would encounter values in ascending order finishing w/ 2nd largest and largest
        in-order: visit left, visit root, visit right
        reverse-in-order: reverse-in-order right, node, reverse-in-order left
        gather into ArrayList, or have counter how many visited

        InterviewCake proposes a different approach: finding largest node and then 2nd largest
        depending if the largest has left subtree
     */

    static void reverseInOrder(BSTNode node) {
        if (node.rgt != null) {
            reverseInOrder(node.rgt);
        }
        System.out.println("node = " + node.value);
        if (node.lft != null) {
            reverseInOrder(node.lft);
        }
    }

    static void reverseInOrder(BSTNode node, ArrayList<Integer> values) {
        if (node.rgt != null) {
            reverseInOrder(node.rgt, values);
        }
        values.add(node.value);
        if (node.lft != null) {
            reverseInOrder(node.lft, values);
        }
    }

    public static void main(String[] args) {
        int size = 20;
        int[] a = Utils.createRandomArray(size, size * 10);
        final BSTNode root = BSTNode.populateFromArray(a);
        System.out.println(BSTNode.printTree(root));

        reverseInOrder(root);

        ArrayList<Integer> values = new ArrayList<>();

        reverseInOrder(root, values);
        System.out.println(values);
    }
}
