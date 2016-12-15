package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jacek on 12/11/16.
 */
public class BSTSequences {
    /*
        find arrays that can produce a given BST if values added in order

        the simple fact to observe is that root is created first so that fixes the 1st element of any such array
        by divide and conquer we see that the 2nd element of the array can only be left or right child
        this gives rise to two families of possible arrays (which therefore mimic the original tree)

        A lot of permutations are possible, subject to constraints that a parent has to precede children in the linearization.
        This is a kind of reverse topological sort
        In fact possible arrays can be generated from the rear towards the front
        Looking at it as topological sort, we can list all nodes (leaves) that do not have dependencies: indegree == 0
        They can be arranged arbitrarily -> remove edges, now some parents become eligible
        Root becomes eligible last

        Starting from the front, we take care of the root
        Then recurse into bifurcation: fill 2nd position with one of root's children
            this leaves the other child or children of the chosen child as candidates for 3rd slot

     */


    static void printArrays(BSTNode root, int N) {
        int[] a = new int[N];
        ArrayList<BSTNode> choices = new ArrayList<>();
        choices.add(root);
        fill(a, 0, choices);
    }

    static void fill(int[] a, int index, ArrayList<BSTNode> choices) {
        if (index == a.length) {
            System.out.println(Arrays.toString(a));
        } else {
            for (int i = 0; i < choices.size(); i++) {
                final ArrayList<BSTNode> choices1 = new ArrayList<>(choices);
                final BSTNode removed = choices1.remove(i);
                a[index] = removed.value;
                if (removed.lft != null) {
                    choices1.add(removed.lft);
                }
                if (removed.rgt != null) {
                    choices1.add(removed.rgt);
                }
                fill(a, index + 1, choices1);
            }
        }
    }

    public static void main(String[] args) {
        final int[] a = Utils.createRandomArrayNoDups(5, 10);
//        final int[] a = {2,1,3};
        // [-1, 1, -6, 7, -5]
        final BSTNode root = new BSTNode(a[0]);
        for (int i = 1; i < a.length; ++i) {
            BSTNode.insert(root, a[i]);
        }
        System.out.println(Arrays.toString(a));
        System.out.println(BSTNode.printTree(root));

        printArrays(root, a.length);
    }
}
