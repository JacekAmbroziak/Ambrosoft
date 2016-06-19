package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jacek on 4/16/16.
 * <p>
 * My implementation of Sedgewick's 3-way trie
 */
public class ThreeWayTrie<Value> {
    private Node root = null;

    private static final class Node {
        final char character;
        Object value;
        Node lt;    // branch for smaller characters
        Node eq;    // branch for equal characters
        Node gt;    // branch for greater characters

        private Node(char character) {
            this.character = character;
        }

        private Object insert(String key, Object value, int d) {
            final char current = key.charAt(d);
            if (current < character) {
                if (lt != null) {
                    return lt.insert(key, value, d);
                } else {
                    lt = path(key, value, d);
                }
            } else if (current > character) {
                if (gt != null) {
                    return gt.insert(key, value, d);
                } else {
                    gt = path(key, value, d);
                }
            } else {    // equal
                if (d + 1 == key.length()) {    // at end: key matches path
                    final Object previous = this.value;
                    this.value = value;
                    return previous;
                } else {
                    if (eq != null) {
                        return eq.insert(key, value, d + 1);
                    } else {
                        eq = path(key, value, d + 1);
                    }
                }
            }
            return null;    // old node found or new path set up
        }

        private void keys(final StringBuilder sb, final ArrayList<String> keyList, final int depth) {
            if (lt != null) {
                lt.keys(sb, keyList, depth);
            }
            {
                sb.append(character);
                if (value != null) {
                    keyList.add(sb.toString());
                }
                if (eq != null) {
                    eq.keys(sb, keyList, depth + 1);
                }
                sb.setLength(depth);
            }
            if (gt != null) {
                gt.keys(sb, keyList, depth);
            }
        }

        String longestPrefixOf(final String prefix, final StringBuilder sb, int depth, final char ch) {
            if (ch < character) {
                return lt != null ? lt.longestPrefixOf(prefix, sb, depth, ch) : null;
            } else if (ch > character) {
                return gt != null ? gt.longestPrefixOf(prefix, sb, depth, ch) : null;
            } else {
                sb.append(character);
                if (eq != null && ++depth < prefix.length()) {
                    final String result = eq.longestPrefixOf(prefix, sb, depth, prefix.charAt(depth));
                    if (result != null) {
                        return result;  // non-null result percolates up
                    } else {
                        sb.setLength(depth);
                    }
                }
                if (value != null) {
                    return sb.toString();
                }
                return null;
            }
        }

        @Override
        public String toString() {
            return "(" + character + ')';
        }
    }

    // set up a new path
    private static Node path(final String key, final Object value, int d) {
        final int keyLength = key.length();
        final Node result = new Node(key.charAt(d));
        {
            Node last = result;
            while (++d < keyLength) {
                last = last.eq = new Node(key.charAt(d));
            }
            last.value = value;
        }
        return result;
    }

    Value insert(final String key, final Value value) {
        if (root != null) {
            return (Value) root.insert(key, value, 0);
        } else {
            root = path(key, value, 0);
            return null;
        }
    }

    Value get(final String key) {
//        final Node node = get(root, key, 0, key.charAt(0));
        final Node node = getIter(root, key);
        return node != null ? (Value) node.value : null;
    }

    Value delete(final String key) {
//        final Node n = get(root, key, 0, key.charAt(0));
        final Node n = getIter(root, key);
        if (n != null) {
            final Value value = (Value) n.value;
            n.value = null;
            // TODO also remove path
            return value;
        } else {
            return null;
        }
    }

    Iterable<String> keys() {
        if (root != null) {
            final ArrayList<String> keyList = new ArrayList<>();
            root.keys(new StringBuilder(), keyList, 0);
            return keyList;
        } else {
            return Collections.emptyList();
        }
    }

    Iterable<String> keysWithPrefix(final String prefix) {
        if (prefix != null) {
            if (prefix.isEmpty()) {
                return keys();
            } else if (root != null) {
//                final Node node = get(root, prefix, 0, prefix.charAt(0));
                final Node node = getIter(root, prefix);
                if (node != null && node.eq != null) {
                    final ArrayList<String> keyList = new ArrayList<>();
                    node.eq.keys(new StringBuilder().append(prefix), keyList, prefix.length());
                    return keyList;
                }
            }
            return Collections.emptyList();
        } else {
            throw new NullPointerException();
        }
    }

    String longestPrefixOf1(final String prefix) {
        if (prefix != null) {
            if (root != null && prefix.length() > 0) {
                return root.longestPrefixOf(prefix, new StringBuilder(), 0, prefix.charAt(0));
            } else {
                return null;
            }
        } else {
            throw new NullPointerException();
        }
    }

    String longestPrefixOf(final String prefix) {
        if (prefix != null) {
            if (root != null && prefix.length() > 0) {
                final int max = longestPrefix(prefix, root);
                if (max >= 0) {
                    return prefix.substring(0, max);
                }
            }
            return null;
        } else {
            throw new NullPointerException();
        }
    }

/*
    private Node get(final Node node, final String key, final int index, final char dthChar) {
        if (node != null) {
            if (dthChar < node.character) {
                return get(node.lt, key, index, dthChar);
            } else if (dthChar > node.character) {
                return get(node.gt, key, index, dthChar);
            } else if (index + 1 < key.length()) {
                return get(node.eq, key, index + 1, key.charAt(index + 1));
            } else {
                return node;
            }
        } else {
            return null;
        }
    }
*/

    private Node getIter(Node node, final String key) {
        int d = 0;
        char ch = key.charAt(0);
        final int prefLen = key.length();
        while (node != null) {
            if (ch < node.character) {
                node = node.lt;
            } else if (ch > node.character) {
                node = node.gt;
            } else if (++d < prefLen) {
                ch = key.charAt(d);
                node = node.eq;
            } else {
                break;
            }
        }
        return node;
    }

    private static int longestPrefix(final String prefix, Node node) {
        int max = -1;
        int d = 0;
        char ch = prefix.charAt(0);
        final int prefLen = prefix.length();

        while (node != null) {
            if (ch < node.character) {
                node = node.lt;
            } else if (ch > node.character) {
                node = node.gt;
            } else {
                if (node.value != null) {
                    max = d + 1;
                }
                if (++d < prefLen) {
                    ch = prefix.charAt(d);
                    node = node.eq;
                } else {
                    break;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        ThreeWayTrie<Integer> twt = new ThreeWayTrie<>();
        twt.insert("evisa", 1);
        twt.insert("ota", 2);
//        twt.insert("evisa", 1);
//        twt.insert("evisa", 1);
//        twt.insert("evisa", 1);
        twt.insert("speloncato", 3);
        twt.insert("spelunca", 4);
        twt.insert("sartene", 5);
        twt.insert("aullene", 6);
        twt.insert("Jacek", 56);
        twt.insert("Marzena", 48);
        twt.insert("xy", 49);
        twt.insert("she", 60);
        twt.insert("shell", 62);
        twt.insert("shells", 63);
        twt.insert("abc", 100);
        twt.insert("abcdef", 101);
        System.out.println("get " + twt.get("spelunca"));
        System.out.println("repl " + twt.insert("spelunca", 5));
        System.out.println("get " + twt.get("spelunca"));
        System.out.println("get " + twt.get("evisa"));
        System.out.println("get " + twt.get("ota"));
        System.out.println("get " + twt.get("speloncato"));
//        System.out.println("del " + twt.delete("ota"));
        System.out.println("get " + twt.get("she"));
        System.out.println("get " + twt.get("shell"));
        System.out.println("get " + twt.get("shells"));
        System.out.println("twt = " + twt);
        System.out.println("longest = " + twt.longestPrefixOf("abcd"));
        System.out.println("longest = " + twt.longestPrefixOf("ab"));
        System.out.println("longest = " + twt.longestPrefixOf("abc"));
        System.out.println("longest = " + twt.longestPrefixOf("abcd"));
        System.out.println("longest = " + twt.longestPrefixOf("abcde"));
        System.out.println("longest = " + twt.longestPrefixOf("abcdef"));
        System.out.println("longest = " + twt.longestPrefixOf("abcdefg"));
        System.out.println("longest = " + twt.longestPrefixOf("abcdefgh"));
        System.out.println("longest = " + twt.longestPrefixOf("xyz"));
        System.out.println("longest = " + twt.longestPrefixOf("speluncato"));
        System.out.println("longest = " + twt.longestPrefixOf("shellsort"));
        System.out.println("longest = " + twt.longestPrefixOf("she"));
        System.out.println("all keys");
        for (String key : twt.keys()) {
            System.out.println("key = " + key);
        }
        {
            System.out.println("with prefix sp");
            for (String key : twt.keysWithPrefix("sp")) {
                System.out.println("key = " + key);
            }
        }
        {
            System.out.println("with prefix a");
            for (String key : twt.keysWithPrefix("a")) {
                System.out.println("key = " + key);
            }
        }
        {
            System.out.println("with prefix x");
            for (String key : twt.keysWithPrefix("x")) {
                System.out.println("key = " + key);
            }
        }
        {
            System.out.println("with prefix sh");
            for (String key : twt.keysWithPrefix("sh")) {
                System.out.println("key = " + key);
            }
        }
    }


}
