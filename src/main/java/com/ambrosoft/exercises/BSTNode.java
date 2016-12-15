package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 12/11/16.
 */

final class BSTNode {
    final int value;
    BSTNode lft;
    BSTNode rgt;
    int count;

    BSTNode(int value) {
        this.value = value;
        this.count = 1;
    }

    static BSTNode insert(BSTNode tree, int value) {
        if (tree == null) {
            return new BSTNode(value);
        } else if (value < tree.value) {
            tree.lft = insert(tree.lft, value);
        } else {
            tree.rgt = insert(tree.rgt, value);
        }
        ++tree.count;
        return tree;
    }

    static String printTree(BSTNode root) {
        final StringBuilder sb = new StringBuilder();
        printTree(sb, root, 0);
        return sb.toString();
    }

    private static void printTree(StringBuilder sb, BSTNode node, int level) {
        if (node != null) {
            sb.append(Utils.Spaces, 0, 2 * level).append(node.value).append(" =").append(node.count).append('\n');
            printTree(sb, node.lft, level + 1);
            printTree(sb, node.rgt, level + 1);
        }
    }

    BSTNode randomNode(final Random random) {
        return select(random.nextInt(this.count));
    }

    BSTNode select(final int rank) {
        final int lCnt = lft != null ? lft.count : 0;
        if (rank < lCnt) {
            return lft.select(rank);
        } else if (rank > lCnt) {
            return rgt.select(rank - lCnt - 1);
        } else {
            return this;
        }
    }
}
