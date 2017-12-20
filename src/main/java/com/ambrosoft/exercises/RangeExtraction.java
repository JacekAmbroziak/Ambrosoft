package com.ambrosoft.exercises;

import java.util.Arrays;

public class RangeExtraction {
    private static String rangeExtraction2(final int[] a) {
        if (a == null) {
            return "";
        } else {
            final StringBuilder sb = new StringBuilder();
            final int length = a.length;
            int offset = 0; // subproblem: considering a[offset..length)

            LOOP:
            while (true) {
                switch (length - offset) {
                    case 0:
                        break LOOP;

                    case 1:
                        sb.append(a[offset++]);
                        break;

                    case 2:
                        sb.append(a[offset++]).append(',').append(a[offset++]);
                        break;

                    default:
                        final int delta = a[offset + 1] - a[offset];
                        switch (delta) {
                            case 1:
                            case -1:
                                int next = offset + 2;
                                while (next < length && a[next] - a[next - 1] == delta) {
                                    ++next;
                                }
                                if (next - offset > 2) {
                                    sb.append(a[offset]).append('-').append(a[next - 1]);
                                    if (next < length) {
                                        sb.append(',');
                                        offset = next;
                                    } else {
                                        break LOOP;
                                    }
                                } else {
                                    sb.append(a[offset++]).append(',');
                                }
                                break;

                            default:
                                sb.append(a[offset++]).append(',');
                                break;
                        }
                }
            }
            return sb.toString();
        }
    }

    private static String rangeExtraction(final int[] a) {
        if (a == null) {
            return "";
        } else {
            final StringBuilder sb = new StringBuilder();
            final int length = a.length;
            int offset = 0; // subproblem: considering a[offset..length)

            LOOP:
            while (true) {
                switch (length - offset) {  // switch on remaining count
                    case 0:
                        break LOOP;

                    case 1:
                        sb.append(a[offset]);
                        break LOOP;

                    case 2:
                        sb.append(a[offset]).append(',').append(a[offset + 1]);
                        break LOOP;

                    default:    // more than 3 -- look for ranges
                        final int delta = a[offset + 1] - a[offset];
                        if (delta == 1 || delta == -1) {
                            int next = offset + 2;
                            while (next < length && a[next] - a[next - 1] == delta) {
                                ++next;
                            }
                            if (next - offset > 2) {
                                sb.append(a[offset]).append('-').append(a[next - 1]);
                                if (next < length) {
                                    sb.append(',');
                                    offset = next;
                                    continue LOOP;
                                } else {
                                    break LOOP;
                                }
                            }
                        }
                        // just output current head
                        sb.append(a[offset++]).append(',');
                        break;
                }
            }
            return sb.toString();
        }
    }

    static String rangeExtraction_shadowmanos(final int[] array) {  // from code wars solutions
        final StringBuilder sb = new StringBuilder();
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            final int rangeStart = array[i];
            sb.append(rangeStart);
            for (int j = i + 1; j <= length; j++) {
                if (j == length || array[j] != rangeStart + (j - i)) {
                    if (j - i >= 3) {
                        sb.append('-').append(array[j - 1]);
                        i = j - 1;
                    }
                    sb.append(',');
                    break;
                }
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println("JRA\t\t\t" + rangeExtraction(a));
        System.out.println("shadowmanos\t" + rangeExtraction_shadowmanos(a));
    }

    public static void main(String[] args) {
        test(new int[]{1});
        test(new int[]{1, 2, 3, 4, 3, 2, 1, 0, 0, 0, 1, 2, 1, 2});
        test(new int[]{-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20});
    }
}
