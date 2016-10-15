package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/8/16.
 * <p>
 * https://projecteuler.net/problem=19
 */
public class CountingSundays {
    static final int SUNDAY = 6;
    /*
    1 Jan 1900 was a Monday.
    Thirty days has September,
    April, June and November.
    All the rest have thirty-one,
    Saving February alone,
    Which has twenty-eight, rain or shine.
    And on leap years, twenty-nine.
    A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
     */

    static int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static boolean isLeapYear(final int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
    }

    public static void main(String[] args) {
        // enumerate all firsts of the month giving day number
        // check which of these, modulo 7, are Sundays

        int year = 1900;
        int dayCount = 0;
        int sundayCount = 0;

        boolean inWindow = false;
        while (year < 2002) {
            dayCount += monthDays[0];   // January
            if (inWindow && dayCount % 7 == SUNDAY) {
                ++sundayCount;
            }
            dayCount += monthDays[1];   // February
            if (isLeapYear(year)) {
                ++dayCount;
            }
            if (inWindow && dayCount % 7 == SUNDAY) {
                ++sundayCount;
            }
            for (int month = 2; month < 12; ++month) {
                dayCount += monthDays[month];
                if (inWindow && dayCount % 7 == SUNDAY) {
                    ++sundayCount;
                }
            }

            ++year;
            inWindow = year >= 1901 && year < 2001;
        }

        System.out.println("sundayCount = " + sundayCount);
    }
}
