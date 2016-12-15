package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 12/10/16.
 */
public class RandomNode {
    public static void main(String[] args) {
        final int[] a = Utils.createRandomArray(20, 40);
        final BSTNode root = new BSTNode(a[0]);
        for (int i = a.length; --i > 0; ) {
            BSTNode.insert(root, a[i]);
        }
        System.out.println(BSTNode.printTree(root));

        final Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            BSTNode r = root.randomNode(random);
            System.out.println("r = " + r.value);
        }
    }
}
