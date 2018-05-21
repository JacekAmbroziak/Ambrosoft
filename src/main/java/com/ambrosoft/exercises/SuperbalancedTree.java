package com.ambrosoft.exercises;

import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created on 12/23/17
 * <p>
 * Leaf depth +1/-1
 * <p>
 * Can't do simple recursive predicate left superbalanced, right superbalanced
 * But we can send up height difference; max at subtree node wins
 * Or not...
 * Left can be superbalanced and right too, and yet between them the difference can be too much
 * So trees should report min/max depth, +1 both at subtree node
 * Iterative solution? BFS? Report leaf found at level: no BFS! Too slow towards leaves
 * DFS + stack of pairs (node,depth) to visit the tree boundary, collect various depth values to discovered if 3 different
 * DFS is faster to discover leaf depths (and their differences)
 */
public class SuperbalancedTree {

    static class NodeDepth {
        final BSTNode node;
        final int depth;

        NodeDepth(BSTNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    static void visitLeaves(final BSTNode root) {
        final TreeSet<Integer> depths = new TreeSet<>();
        final Deque<NodeDepth> stack = new LinkedList<>();
        stack.push(new NodeDepth(root, 0));
        while (!stack.isEmpty()) {
            final NodeDepth nodeDepth = stack.pop();
            final BSTNode node = nodeDepth.node;
            final int depth = nodeDepth.depth;
            if (node.lft == null && node.rgt == null) {
                if (depths.add(depth)) {
                    System.out.println(depths.size());
                }
            } else {
                if (node.lft != null) {
                    stack.push(new NodeDepth(node.lft, depth + 1));
                }
                if (node.rgt != null) {
                    stack.push(new NodeDepth(node.rgt, depth + 1));
                }
            }
        }
    }

    public static void main(String[] args) {
        int size = 20;
        int[] a = Utils.createRandomArray(size, size * 10);
        final BSTNode root = BSTNode.populateFromArray(a);
        System.out.println(BSTNode.printTree(root));
        visitLeaves(root);
    }
}
