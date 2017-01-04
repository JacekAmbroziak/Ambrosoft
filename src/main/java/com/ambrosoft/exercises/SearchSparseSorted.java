package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/2/17.
 */
public class SearchSparseSorted {
    /*
        sorted String array except from any number of empty Strings which break simple binary search
        suggests a modified binary search with some linear scans for nonempty strings

        worst case: all empty Strings

        we may ensure that an interval we search starts and ends with non-empty string
        binary search trouble:
        when midpoint hits empty, we have no idea if elt can be on the left or right

        Alternative:

        divide array in halves

        from middle (inclusive) scan left in search of non-empty
        if found and greater than elt, work with remaining part of left
        if smaller than elt, abandon and work with right half scanning from left to right

        we should have several FAIL values: one to mean continue on other side, and one to mean global fail
     */


    static int goLeft(final String[] data, int from, final int to) {
        while (from > to) {
            if (data[from].isEmpty()) {
                --from;
            } else {
                return from;
            }
        }
        return -1;
    }

    static int goRight(final String[] data, int from, final int to) {
        while (from < to) {
            if (data[from].isEmpty()) {
                ++from;
            } else {
                return from;
            }
        }
        return -1;
    }

    // searches for non-empty value left and right of start, within bounds [lo, hi]
    static int pendulum(String[] data, int start, int lo, int hi) {
        if (data[start].isEmpty()) {
            int lft = start - 1;
            int rgt = start + 1;

            while (lft >= lo && rgt <= hi) {
                if (data[lft].isEmpty()) {
                    --lft;
                } else {
                    return lft;
                }
                if (data[rgt].isEmpty()) {
                    ++rgt;
                } else {
                    return rgt;
                }
            }
            while (lft >= lo) {
                if (data[lft].isEmpty()) {
                    --lft;
                } else {
                    return lft;
                }
            }
            while (rgt <= hi) {
                if (data[rgt].isEmpty()) {
                    ++rgt;
                } else {
                    return rgt;
                }
            }
            return -1;
        } else {
            return start;
        }
    }

    // contract: if el in data, it must be within [lo, hi]
    static int searchAux(String[] data, String el, int lo, int hi) {
        if (lo < hi) {
            final int middle = (lo + hi) / 2;
            if (data[middle].isEmpty()) {
                // we need to look left and right for some non-empty element
                int lft = goLeft(data, middle - 1, lo);


                return 0;
            } else {
                int cmp = el.compareTo(data[middle]);
                if (cmp == 0) {
                    return middle;
                } else if (cmp < 0) {
                    return searchAux(data, el, lo, middle - 1);
                } else {
                    return searchAux(data, el, middle + 1, hi);
                }
            }
        } else {
            return -1;
        }
    }

    static int search(String[] data, String el) {
        final int lo = goRight(data, 0, data.length);
        if (lo < 0) {
            return -1;
        } else {
            final int cmpl = el.compareTo(data[lo]);
            if (cmpl == 0) {
                return lo;
            } else if (cmpl < 0) {
                return -1;
            } else {
                final int hi = goLeft(data, data.length - 1, lo);
                if (hi < 0) {
                    return -1;
                } else {
                    final int cmpr = el.compareTo(data[hi]);
                    if (cmpr == 0) {
                        return hi;
                    } else if (cmpr > 0) {
                        return -1;
                    } else {
                        return searchAux(data, el, lo + 1, hi - 1);
                    }
                }
            }
        }
    }

    static int searchSimple(String[] data, String el) {
        return searchSimpleHelper(data, el, 0, data.length - 1);
    }

    static int searchSimpleHelper(String[] data, String el, int lo, int hi) {
        if (lo < hi) {
            final int nonEmpty = pendulum(data, (lo + hi) / 2, lo, hi);
            if (nonEmpty < 0) {
                return -1;
            } else {
                final int cmp = el.compareTo(data[nonEmpty]);
                if (cmp == 0) {
                    return nonEmpty;
                } else if (cmp < 0) {
                    return searchSimpleHelper(data, el, lo, nonEmpty - 1);
                } else {
                    return searchSimpleHelper(data, el, nonEmpty + 1, hi);
                }
            }
        } else {
            return -1;
        }
    }

    static void test(String[] data, String el) {
        System.out.println(Arrays.toString(data));
        System.out.println(String.format("%s -> %d", el, searchSimple(data, el)));
    }

    public static void main(String[] args) {
        String[] strings = new String[]{"", "", "a", "", "", "b", "", "", "", "c", "", "", "e", "", "f", "", "", "i", "", "", "", ""};

//        test(new String[]{"", "", "b", "", "c", "", "", "d", "", "f", "",}, "c");
        test(new String[]{"a", "", "", "", "", "", "", "", "", "", "b",}, "b");
//        test(new String[]{"", "", "", "a", "", "", "", "", "", "", "",}, "a");
    }
}
