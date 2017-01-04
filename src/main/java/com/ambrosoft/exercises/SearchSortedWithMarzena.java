package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/31/16.
 */
public class SearchSortedWithMarzena {

    static int add(int x, int y) {
        return x + y;
    }

    static int search(String[] strings, String s) {
        System.out.println("------------------------");
        int index = 0;
        while (index < strings.length) {
            System.out.println("index = " + index + "  =  " + strings[index]);
            if (strings[index].equals(s)) {
                System.out.println("found!");
                return index;
            }
            index++;
        }
        System.out.println("koniec petli: not found, returning -1");
        return -1;
    }

    static int binarySearch(String[] strings, String sought) {
        return binarySearchHelper(strings, sought, 0, strings.length);
    }

    static int binarySearchHelper(String[] strings, String sought, int from, int to) {
        if (from < to) {
            final int middle = (from + to) / 2;
            final String middleElement = strings[middle];
            final int cmp = sought.compareTo(middleElement);
            if (cmp < 0) {
                return binarySearchHelper(strings, sought, from, middle);
            } else if (cmp > 0) {
                return binarySearchHelper(strings, sought, middle + 1, to);
            } else {
                System.out.println("FOUND!");
                return middle;
            }
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello Marzena! " + add(4, 8));
        String[] strings = new String[]{"aa", "b", "c", "e", "f", "", "g", "k"};
        System.out.println("strings = " + strings.length);
        System.out.println("found = " + binarySearch(strings, "d"));
    }
}
