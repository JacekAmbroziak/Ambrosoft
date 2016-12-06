package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/28/16.
 */

public class ListInterleave {

    static class Cell {
        final char data;
        Cell next;

        Cell(char data) {
            this.data = data;
        }

        Cell(char data, Cell next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(data);
            for (Cell ptr = next; ptr != null; ptr = ptr.next) {
                sb.append(',').append(' ').append(ptr.data);
            }
            return sb.toString();
        }

        Cell append(final Cell list) {
            Cell ptr = this;
            while (ptr.next != null) {
                ptr = ptr.next;
            }
            ptr.next = list;
            return this;
        }

        int length() {
            int count = 1;
            Cell ptr = this;
            while ((ptr = ptr.next) != null) {
                ++count;
            }
            return count;
        }

        Cell traverse(int steps) {
            Cell ptr = this;
            while (--steps >= 0 && (ptr = ptr.next) != null) {
            }
            return ptr;
        }
    }

    static Cell fromArray(char[] chars) {
        int index = chars.length - 1;
        Cell list = new Cell(chars[index]);
        while (--index >= 0) {
            list = new Cell(chars[index], list);
        }
        return list;
    }

    static Cell fromString(String string) {
        return fromArray(string.toCharArray());
    }

    static Cell interleave(final Cell list) {
        // can find middle w/ 2 pointers, slow and fast, or w/ length
        final int length = list.length();
        if (length % 2 == 0) {
            final Cell lastA = list.traverse(length / 2 - 1);
            for (Cell ptr = list; ptr != lastA; ) {
                final Cell nextA = ptr.next;
                final Cell b = lastA.next;
                lastA.next = b.next;
                ptr.next = b;
                b.next = nextA;
                ptr = nextA;
            }
            return list;
        } else {
            throw new IllegalArgumentException("odd length");
        }
    }

    public static void main(String[] args) {
        Cell c1 = fromString("abcdefgh");
        System.out.println("c1 = " + c1);
        System.out.println("c1 = " + c1.length());

        Cell c2 = fromString("12345678");

        System.out.println("c2 = " + c2);

        c1.append(c2);
        System.out.println("c1 = " + c1);
        System.out.println("c1 = " + c1.length());
        System.out.println("c1 = " + c1.traverse(8));
        System.out.println("c1 = " + interleave(c1));
        System.out.println("c1 = " + interleave(fromString("AB12")));
    }
}
