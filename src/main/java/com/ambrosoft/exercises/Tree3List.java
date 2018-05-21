package com.ambrosoft.exercises;

/**
 * Created on 12/22/17
 *
 * Save all leaf nodes of a Binary tree in a Doubly Linked List by using Right node as Next node and Left Node as Previous Node
 *
 * Approaches:
 * 1) walk the tree in-order, when at leaf append to end of list
 * 2) recursive: map L subtree to list, same for R and append
 */
public class Tree3List {


    public static void main(String[] args) {
        int size = 10;
        int[] a = Utils.createRandomArray(size, size * 10);

        BSTNextValue.BSTNode root = BSTNextValue.buildRandomTree(a);

        System.out.println(root);

    }

}
