package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/20/17
 * <p>
 * InterviewCake
 * <p>
 * No division, multiplication only.
 * Observation: drawing a1,a2,a3,a4,a5,a6,a7,a8
 * Computation of eg. a4,a5 shares similar product prefixes and suffixes
 * Luckily they are contiguous prefix is extending, suffix is shrinking
 * So 2 passes: muliply in growing prefixes, then backwards growing suffixes
 */
public class ProductsInArray {
    static int[] getProductsOfAllIntsExceptAtIndex(int[] a) {
        final int len = a.length;
        final int[] result = new int[len];
        int prefixProduct = 1;
        for (int i = 0; i < len; i++) {
            result[i] = prefixProduct;
            prefixProduct *= a[i];
        }
        int suffixProduct = 1;
        for (int i = len; --i >= 0; ) {
            result[i] *= suffixProduct;
            suffixProduct *= a[i];
        }
        return result;
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(getProductsOfAllIntsExceptAtIndex(a)));
    }

    public static void main(String[] args) {
        test(new int[]{1, 7, 3, 4});
        test(new int[]{1, 2, 6, 5, 9});
        test(new int[]{1, 2, 0, 5, 9});
    }
}
