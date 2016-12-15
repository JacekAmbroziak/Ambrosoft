package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/9/16.
 */
public class CommonAncestor {
    /*
        first common ancestor of 2 nodes in a binary tree

        it would be easiest to walk up ancestry path storing nodes in a Set


     */

    static class Result {
        TreeNode common;
        boolean n1Found;
        boolean n2Found;

        Result(TreeNode common) {
            this.common = common;
        }
    }

    static class TreeNode {
        int datum;
        TreeNode lft;
        TreeNode rgt;

        @Override
        public String toString() {
            return String.valueOf(datum);
        }

        int sum() {
            int result = datum;
            if (lft != null) {
                result += lft.sum();
            }
            if (rgt != null) {
                result += rgt.sum();
            }
            return result;
        }

        TreeNode find(final int value) {
            if (datum == value) {
                return this;
            } else if (lft != null) {
                final TreeNode n1 = lft.find(value);
                if (n1 != null) {
                    return n1;
                } else if (rgt != null) {
                    final TreeNode n2 = rgt.find(value);
                    if (n2 != null) {
                        return n2;
                    }
                }
            }
            return null;
        }

        /*
            Looking from the perspective of a node, it discovers itself to be the first common ancestor
            iff one node was found in its left subtree, and another in its right subtree.
            It can then return itself to propagate.
         */

        boolean find(final TreeNode node) {
            return this == node || leftFind(node) || rightFind(node);
        }

        private boolean leftFind(final TreeNode node) {
            return lft != null && lft.find(node);
        }

        private boolean rightFind(final TreeNode node) {
            return rgt != null && rgt.find(node);
        }

        /*
            it would be faster to implement "explore(n1, n2)" that can return
            - nothing found
            - common ancestor found
            - n1 found
            - n2 found
         */

        TreeNode findCommonAncestor(final TreeNode n1, final TreeNode n2) {
            if (this == n1 && (leftFind(n2) || rightFind(n2))) {
                return this;
            }
            if (this == n2 && (leftFind(n1) || rightFind(n1))) {
                return this;
            }

            if (leftFind(n1) && rightFind(n2) || leftFind(n2) && rightFind(n1)) {
                return this;
            }

            TreeNode common = null;
            if (lft != null) {
                common = lft.findCommonAncestor(n1, n2);
            }
            if (common == null && rgt != null) {
                common = rgt.findCommonAncestor(n1, n2);
            }
            return common;
        }

        Result explore(TreeNode n1, TreeNode n2) {
            if (this == n1 && (leftFind(n2) || rightFind(n2))) {
                return new Result(this);
            }
            if (this == n2 && (leftFind(n1) || rightFind(n1))) {
                return new Result(this);
            }

            Result result = null;
            if (lft != null) {
                result = lft.explore(n1, n2);
                if (result != null) {
                    if (result.common != null) {
                        return result;
                    } else if (result.n1Found && rightFind(n2) || result.n2Found && rightFind(n1)) {
                        return new Result(this);
                    }
                }
            }
            if (rgt != null) {
                result = rgt.explore(n1, n2);
                if (result != null) {
                    if (result.common != null) {
                        return result;
                    } else if (result.n1Found && leftFind(n2) || result.n2Found && leftFind(n1)) {
                        return new Result(this);
                    }
                }
            }
            return result;
        }
    }

    boolean find(TreeNode node, TreeNode n) {
        return node != null && (node == n || find(node.lft, n) || find(node.rgt, n));
    }

    // TODO finish this
    Result explore(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return null;
        } else {
            return new Result(node);
        }
    }


    static TreeNode buildTree(int[] a) {
        return buildTree(a, 0, a.length);
    }

    static String printTree(TreeNode root) {
        final StringBuilder sb = new StringBuilder();
        printTree(sb, root, 0);
        return sb.toString();
    }

    private static void printTree(StringBuilder sb, TreeNode node, int level) {
        if (node != null) {
            sb.append(Utils.Spaces, 0, 2 * level).append(node).append('\n');
            printTree(sb, node.lft, level + 1);
            printTree(sb, node.rgt, level + 1);
        }
    }

    private static TreeNode buildTree(int[] a, int start, int end) {
        if (end > start) {
            final int mid = start + (end - start) / 2;
            final TreeNode node = new TreeNode();
            node.datum = a[mid];
            node.lft = buildTree(a, start, mid);
            node.rgt = buildTree(a, mid + 1, end);
            return node;
        } else {
            return null;
        }
    }

    static TreeNode findCommonAncestor(TreeNode root, TreeNode n1, TreeNode n2) {
        return root.findCommonAncestor(n1, n2);
    }

    public static void main(String[] args) {
//        int[] a = Utils.createRandomArray(30, 100);
        int[] a = {35, 72, 64, 22, 31, 3, 4, 60, 91, 50, 5, 4, 69, 39, 68, 92, 77, 85, 37, 41, 35, 95, 64, 37, 18, 30, 47, 78, 49, 30};
        System.out.println(Arrays.toString(a));
        TreeNode root = buildTree(a);
        System.out.println("root.sum() = " + root.sum());
        int sum = 0;
        for (int i : a) {
            sum += i;
        }
        System.out.println("sum = " + sum);
        System.out.println(printTree(root));
        TreeNode n1 = root.find(5);
        TreeNode n2 = root.find(68);
        TreeNode common = findCommonAncestor(root, n1, n2);
        System.out.println("common = " + common);
        Result result = root.explore(n1, n2);
        System.out.println("result = " + result);
    }
}