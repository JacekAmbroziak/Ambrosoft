package com.ambrosoft.exercises;

/*
    11/15/2017

    Problem:
    You have two very large binary trees: T1, with millions of nodes, and T2, with hundreds of nodes.
    Create an algorithm to decide if T2 is a subtree of T1

    Analysis/brainstorming:
    (I typically create such written analyses for tougher problems, with drawings.
    Final form should be edited to remove ideas rejected)

    Assuming that nodes hold values and that value equality is a necessary condition if nodes are to match
    (but not sufficient -- children may not match)

    (There can be a more difficult variant of this problem if T2 was to match nodes and edges of T1 but where nodes
    corresponding to T2's leaves could have children in T1.
    But in this case we will assume that T2's leaves will correspond to T1's leaves.)
    The matching of the leaves is an important observation;
    a corollary is that the height of T2
    constrains possibly matching roots of T1's subtrees to be high enough in T1

    One can always attack this problem with a brute force approach:
    1) define a recursive function isEqual(Node n1, Node n2) in the obvious way: compare value,
       require equality of children if present
    2) traverse T1 in some order and for every node of T1 test isEqual(n, root-of-T2)

    While matching nodes from T1 and T2, we compare values stored in the nodes; this comparison is an operation (OP)
    OP can itself be expensive. We would ideally try to reduce the number of OPs

    We may be presented with mischievous input where T1 and T2 are both balanced and containing the same values
    except the rightmost leaf; there would be A LOT of node testing before T2 is found in T1 as last subtree.

    The time complexity of brute force solution would be big
    A more effective algorithm would need to prune the number of OPs

    Since T2 is smaller perhaps we can precompute some data structure from T2 ("signature")
    such that searching for this structure in T1 is simpler than the recursive "isEqual"
    That data structure can be eg. a serialization of T2 into an array or string,
    or some form of "hash function".
    We would then scan T1, incrementally updating an analogous serialization and/or hash, searching for a match

    In general, the problem of performance optimization here is very hard w/o any simplifying assumptions
    We don't know any statistics on T1, such as heights of nodes,
    and can only explore the tree by following links to children

    In the worst case, as mentioned above, T2's root node's value may occur *multiple* times in T1
    making filtering on that value ineffective, leading to a lot of node matching (OPs)

    For example, if T2's node n holds value V, and we are looking at a node in T1 that also holds the value V,
    and it has 2 non-empty children both holding V as well, then we can have a match at n or at any of the children
    (we don't know the heights of these nodes)

    As we noticed above, T2 as a smaller tree can more readily be preprocessed to eg. a hash or a serialized form,
    but *if we need to search T1 repeatedly* we would benefit from preprocessing/"indexing"/augmenting T1 as well,
    for example caching node heights and subtree node counts


    Additional ideas:

    1) try using node height information in recursive matching
    2) add subtree height information to serialization
    3) parallelize


 */

import java.util.concurrent.ThreadLocalRandom;

public class SubtreeTest {

    static final int RANDOM_TREE_VALUE_BOUND = 1000;

    // making the solution more general
    interface Datum {
        boolean equals(final Datum other);

        // sb will be modified
        void serialize(final StringBuilder sb);

        Datum copy();
    }

    static final class IntegerDatum implements Datum {
        private final int value;

        IntegerDatum(int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }

        @Override
        public boolean equals(final Datum other) {
            return other instanceof IntegerDatum && ((IntegerDatum) other).value == value;
        }

        @Override
        public void serialize(final StringBuilder sb) {
            sb.append(value);
        }

        public IntegerDatum copy() {
            return new IntegerDatum(value);
        }
    }

    static class BinaryTreeNode {
        final Datum datum;
        BinaryTreeNode lft;
        BinaryTreeNode rgt;

        BinaryTreeNode(Datum datum) {
            this.datum = datum;
        }

        // this function to be called with non-null argument
        boolean equals(final BinaryTreeNode other) {
            if (!datum.equals(other.datum)) {
                return false;
            } else if (lft == null) {
                return other.lft == null;
            } else if (rgt == null) {
                return other.rgt == null;
            } else {
                // other.child != null tests performed here as they are cheap and can help avoid function calls
                // plus they guarantee non-nullness of this function's arg
                return other.lft != null && other.rgt != null && lft.equals(other.lft) && rgt.equals(other.rgt);
            }
        }

        int height() {
            return Math.max(lft != null ? lft.height() : -1, rgt != null ? rgt.height() : -1) + 1;
        }

        private static void serializeChild(final StringBuilder sb, final BinaryTreeNode child) {
            if (child != null) {
                child.serialize(sb.append(' '));    // prepend space as separator before serializing value
            } else {
                sb.append('n'); // 'n' for null
            }
        }

        void serialize(final StringBuilder sb) {
            datum.serialize(sb);
            serializeChild(sb, lft);
            serializeChild(sb, rgt);
        }

        BinaryTreeNode copy() {
            final BinaryTreeNode result = new BinaryTreeNode(datum.copy());
            if (lft != null) {
                result.lft = lft.copy();
            }
            if (rgt != null) {
                result.rgt = rgt.copy();
            }
            return result;
        }

        BinaryTreeNode pickRandomNodeAtLevel(int level) {
            if (level < 0) {
                throw new IllegalArgumentException("level cannot be negative");
            }
            if (level == 0) {
                return this;
            } else {
                final boolean goLeft = ThreadLocalRandom.current().nextBoolean();
                if (goLeft) {
                    return lft != null ? lft.pickRandomNodeAtLevel(level - 1) : null;
                } else {
                    return rgt != null ? rgt.pickRandomNodeAtLevel(level - 1) : null;
                }
            }
        }
    }

    static class BinaryTree {
        final BinaryTreeNode root;

        BinaryTree(BinaryTreeNode root) {
            this.root = root;
        }

        boolean isEmpty() {
            return root == null;
        }

        boolean containsViaSerialization(final BinaryTree subtree) {
            if (subtree.isEmpty()) {
                return true;
            } else if (isEmpty()) {
                return false;
            } else {
                final StringBuilder sb = new StringBuilder();
                serialize(sb);
                return sb.indexOf(subtree.serializeToString()) >= 0;
            }
        }

        boolean containsRecursive(final BinaryTree subtree) {
            return subtree.isEmpty() || !isEmpty() && containsRecursiveAux(root, subtree.root);
        }

        private boolean containsRecursiveAux(BinaryTreeNode t1node, BinaryTreeNode t2root) {
            return t2root.equals(t1node) ||
                    t1node.lft != null && containsRecursiveAux(t1node.lft, t2root) ||
                    t1node.rgt != null && containsRecursiveAux(t1node.rgt, t2root);
        }

        int height() {
            if (isEmpty()) {
                throw new IllegalStateException("height of empty tree undefined");
            }
            return root.height();
        }

        void serialize(final StringBuilder sb) {
            root.serialize(sb);
        }

        String serializeToString() {
            final StringBuilder sb = new StringBuilder();
            serialize(sb);
            return sb.toString();
        }

        BinaryTree copy() {
            return new BinaryTree(root != null ? root.copy() : null);
        }

        static BinaryTree createRandomSubtree(final int height) {
            if (height < 0) {
                throw new IllegalArgumentException("need non negative tree height");
            }
            return new BinaryTree(createRandomSubtree(height, ThreadLocalRandom.current()));
        }

        BinaryTree pickRandomSubtreeAtLevel(final int level) {
            if (level < 0) {
                throw new IllegalArgumentException("level cannot be negative");
            }
            if (root == null) {
                return new BinaryTree(null);
            } else {
                return new BinaryTree(root.pickRandomNodeAtLevel(level));
            }
        }

        private static BinaryTreeNode createRandomSubtree(int height, ThreadLocalRandom random) {
            if (height >= 0) {
                final BinaryTreeNode node = new BinaryTreeNode(new IntegerDatum(random.nextInt(RANDOM_TREE_VALUE_BOUND)));
                node.lft = createRandomSubtree(height - 1, random);
                node.rgt = createRandomSubtree(height - 1, random);
                return node;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = BinaryTree.createRandomSubtree(5);
        BinaryTree subtree = tree.pickRandomSubtreeAtLevel(2).copy();

        System.out.println(tree.serializeToString());
        System.out.println(subtree.serializeToString());
        boolean contains1 = tree.containsViaSerialization(subtree);
        System.out.println("contains1 = " + contains1);

        boolean contains2 = tree.containsRecursive(subtree);
        System.out.println("contains basic recursive = " + contains2);

        System.out.println("cont3 = " + subtree.containsRecursive(subtree));
    }

}
