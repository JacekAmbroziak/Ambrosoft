package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/29/16.
 */
public class BSTNextValue {

    final static class BSTNode {
        final int value;
        final BSTNode parent;
        BSTNode lft;
        BSTNode rgt;

        BSTNode(int val, BSTNode parent) {
            value = val;
            this.parent = parent;
        }

        // return node with value val
        BSTNode insert(final int val) {
            if (val < value) {
                if (lft != null) {
                    return lft.insert(val);
                } else {
                    return lft = new BSTNode(val, this);
                }
            } else if (val > value) {
                if (rgt != null) {
                    return rgt.insert(val);
                } else {
                    return rgt = new BSTNode(val, this);
                }
            } else {
                return this;
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (lft != null) {
                sb.append('(').append(lft.toString()).append(')');
            }
            sb.append('<').append(value).append('>');
            if (rgt != null) {
                sb.append('(').append(rgt.toString()).append(')');
            }
            return sb.toString();
        }

        void stringRep(final StringBuilder sb) {
            if (lft != null) {
                sb.append('(');
                lft.stringRep(sb);
                sb.append(')');
            }
            sb.append('<').append(value).append('>');
            if (rgt != null) {
                sb.append('(');
                rgt.stringRep(sb);
                sb.append(')');
            }
        }
    }

    static BSTNode buildRandomTree(int[] a) {
        BSTNode root = new BSTNode(a[0], null);
        for (int i = a.length; --i > 0; ) {
            root.insert(a[i]);
        }
        return root;
    }

    static BSTNode leftmost(BSTNode node) {
        BSTNode left;
        while ((left = node.lft) != null) {
            node = left;
        }
        return node;
    }

    static BSTNode rightmost(BSTNode node) {
        BSTNode right;
        while ((right = node.rgt) != null) {
            node = right;
        }
        return node;
    }

    static BSTNode successor(BSTNode node) {
        // 2 cases when there is no successor
        // a) node is rightmost, ancestral path always along right edges
        // b) tree is unbalanced (or one element), we first at root, and it has no right child
        // otherwise successor is first ancestor reached from left child   WRONG!
        // right subtree of node contains nodes bigger than node but smaller than parent of node
        // so if the subtree exists, return its minimal element
        // if not, go up, and return first parent reached from the left

        if (node.rgt != null) {
            return leftmost(node.rgt);  // smallest in right child tree
        } else {
            BSTNode parent;
            // look for an ancestor that can be visited from left edge
            while ((parent = node.parent) != null && parent.rgt == node) {
                node = parent;
            }
            return parent;
        }
    }

    static String val(BSTNode node) {
        return node != null ? String.valueOf(node.value) : "NULL";
    }

    public static void main(String[] args) {
        int size = 20;
        int[] a = Utils.createRandomArray(size, size * 10);

        BSTNode root = buildRandomTree(a);
        System.out.println(root);
        BSTNode node = root.insert(a[0]);
        System.out.println(root);
        BSTNode min = leftmost(root);
        System.out.println("min = " + min.value);
        BSTNode max = rightmost(root);
        System.out.println("max = " + max.value);
        BSTNode succ = successor(node);
        System.out.println(node.value + " succ = " + succ + "\t" + val(succ));
        BSTNode minsucc = successor(min);
        BSTNode maxsucc = successor(max);

        System.out.println(min.value + " succ = " + minsucc + "\t" + val(minsucc));
        System.out.println(max.value + " succ = " + maxsucc + "\t" + val(maxsucc));
    }
}
