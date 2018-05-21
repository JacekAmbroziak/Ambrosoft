package com.ambrosoft.exercises;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 3/15/16.
 */
public class FindInTree {

    static final class Node {
        int x;
        int y;
        Node lft;
        Node rgt;
        int level;  // for testing only

        int count() {
            int count = 1;  // self
            if (lft != null) {
                count += lft.count();
            }
            if (rgt != null) {
                count += rgt.count();
            }
            return count;
        }

        @Override
        public String toString() {
            return "(" + x + ',' + y + ')';
        }
    }

    static Node generateRandomTree(final int nodeCount, final int bound) {
        final Random random = ThreadLocalRandom.current();
        final Node[] nodes = new Node[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            final Node node = new Node();
            node.x = random.nextInt(bound);
            node.y = random.nextInt(bound);
            nodes[i] = node;
        }
        // shuffling not really necessary
        shuffleArray(nodes);

        return buildTree(nodes, 0, nodeCount - 1, 0);
    }

    private static Node buildTree(final Node[] nodes, final int lo, final int hi, int level) {
        if (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            final Node result = nodes[mid];
            result.level = level;
            result.lft = buildTree(nodes, lo, mid - 1, level + 1);
            result.rgt = buildTree(nodes, mid + 1, hi, level + 1);
            return result;
        } else {
            return null;
        }
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(final Node[] array) {
        final Random rnd = ThreadLocalRandom.current();
        for (int i = array.length; --i > 0; ) {
            final int index = rnd.nextInt(i + 1);
            final Node node = array[index];
            array[index] = array[i];
            array[i] = node;
        }
    }

    static void levelOrder(final Node root) {
        final Queue<Node> nodeQueue = new LinkedList<>();
        final Queue<Integer> levelQueue = new LinkedList<>();
        nodeQueue.add(root);
        levelQueue.add(0);
        int count = 0;
        do {
            final Node node = nodeQueue.remove();
            final Integer nextLevel = levelQueue.remove() + 1;
            if (node.lft != null) {
                nodeQueue.add(node.lft);
                levelQueue.add(nextLevel);
            }
            if (node.rgt != null) {
                nodeQueue.add(node.rgt);
                levelQueue.add(nextLevel);
            }
            ++count;
            assert node.level == nextLevel - 1;
        } while (nodeQueue.size() > 0);
        System.out.println("walk count = " + count);
    }

    static boolean levelOrderSearch(final Node root, final int x, final int y) {
        final Queue<Node> nodeQueue = new LinkedList<>();
        final Queue<Integer> levelQueue = new LinkedList<>();
        final Set<Integer> xlevel = new HashSet<>();
        final Set<Integer> ylevel = new HashSet<>();
        nodeQueue.add(root);
        levelQueue.add(0);
        do {
            final Node node = nodeQueue.remove();
            final Integer currentLevel = levelQueue.remove();

            if (node.x == x) {  // x found
                if (ylevel.contains(currentLevel)) {
                    return true;
                } else {
                    xlevel.add(currentLevel);
                }
            }
            if (node.y == y) {  // y found
                if (xlevel.contains(currentLevel)) {
                    return true;
                } else {
                    ylevel.add(currentLevel);
                }
            }

            final Integer nextLevel = currentLevel + 1;
            if (node.lft != null) {
                nodeQueue.add(node.lft);
                levelQueue.add(nextLevel);
            }
            if (node.rgt != null) {
                nodeQueue.add(node.rgt);
                levelQueue.add(nextLevel);
            }
            assert node.level == nextLevel - 1;
        } while (nodeQueue.size() > 0);
        return false;
    }

    static Node randomWalk(Node node, int depth) {
        if (node != null && depth > 0) {
            final Random random = ThreadLocalRandom.current();
            do {
                node = random.nextBoolean() ? node.rgt : node.lft;
            } while (--depth > 0 && node != null);
        }
        return node;
    }


    public static void main(String[] args) {
        Node root = generateRandomTree(64 * 4, 4096);
        System.out.println("root = " + root);
        System.out.println("count = " + root.count());
        levelOrder(root);

        final int level = 7;
        final int x = -13;
        final int y = -17;

//        root.x = x;
//        root.y = y;

        System.out.println("levelOrderSearch(root, x,y) = " + levelOrderSearch(root, x, y));
        {
            Node n1 = randomWalk(root, level);
            Node n2 = randomWalk(root, level);
            if (n1 != null && n2 != null) {
                n1.x = x;
                n2.y = y;
            } else {
                System.out.println("node not found at level " + level);
            }
        }
        System.out.println("levelOrderSearch(root, x,y) = " + levelOrderSearch(root, x, y));

        System.out.println("done");
    }

}
