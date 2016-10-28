package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/24/16.
 *
 * Gayle, p.186, 17.4
 * Array, all numbers from 0..n with one missing
 * access is to individual bits only a(i, j): j-th bit of a[i]
 *
 * Find missing integer
 */
public class MissingNumber {
    /*
        approach:
        missing is either even or odd
        from n we know how many even and odd numbers are in 0..n
        looking at least significant bit we learn if missing is even/odd
        then one can look at the next bit
     */
}
