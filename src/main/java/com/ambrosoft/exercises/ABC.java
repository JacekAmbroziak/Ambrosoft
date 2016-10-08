package com.ambrosoft.exercises;

/**
 * Created by jacek on 9/4/16.
 * <p>
 * n characters
 * a, b, or c
 * up to 2 consecutive c's
 * at most one b
 */
public class ABC {

    public static void main(String[] args) {
//        int n = 4;
        for (int n = 5; n < 6; ++n) {
            System.out.println("n = " + n);
            System.out.println("countAll = " + countABC(n));
//            System.out.println("count = " + count("", n));
        }
    }

    static int countABC(final int n) {
        System.out.println("abc n = " + n);
        return n == 1 ? 3 : countABC(n - 1) + // after a
                countWithoutB(n - 1) + // after b
                countAfterC(n - 1);
    }

    static int countWithoutB(final int n) {
        System.out.println("ac n = " + n);
        // a or c
        return n == 1 ? 2 : countWithoutB(n - 1) + countAfterCandWithoutB(n - 1);
    }

    static int countAfterC(final int n) {
        System.out.println("after c n = " + n);
        return n == 1 ? 2 : countABC(n - 1) + // a
                countWithoutB(n - 1);
    }

    static int countAfterCandWithoutB(final int n) {
        System.out.println("after c, no b n = " + n);
        return n == 1 ? 1 : countWithoutB(n - 1);
    }


    ///////

    public static int count(final String str, final int remaining) {
        if (remaining <= 0) {
//            System.out.println(str);
            return 1;
        } else {
            int sum = 0;
            final int length = str.length();
            if (str.indexOf('b') < 0) {
                sum += count(str + 'a', remaining - 1);
                sum += count(str + 'b', remaining - 1);
                if (canAddC(str, length)) {
                    sum += count(str + 'c', remaining - 1);
                }
            } else {
                sum += count(str + 'a', remaining - 1);
                if (canAddC(str, length)) {
                    sum += count(str + 'c', remaining - 1);
                }
            }
            return sum;
        }
    }

    private static boolean canAddC(final String str, final int length) {
        return length == 0 || str.charAt(length - 1) != 'c';
    }

}
