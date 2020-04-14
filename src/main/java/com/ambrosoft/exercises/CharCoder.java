package com.ambrosoft.exercises;

import java.nio.charset.StandardCharsets;
import java.util.Random;


/**
 * Copyright Ambrosoft, Inc. 2020
 * Date: Oct 22, 2006
 * Time: 11:18:20 AM
 */

final class CharCoder {
    private static final int LOOP = 1000;

    static String encode(final byte[] bytes, final int byteCount) {
        final int charCount = ((byteCount + 1) >> 1) + 1, fullCharCount = (byteCount >> 1) + 1;
        final char[] chars = new char[charCount];
        int i = 1, j = 0;
        chars[0] = (char) byteCount;
        for (; i < fullCharCount; i++) {
            chars[i] = (char) ((bytes[j++] & 0xFF) << 8 | (bytes[j++] & 0xFF));
        }
        if (j < byteCount) {    // one more byte
            chars[i] = (char) (bytes[j] & 0xFF);
        }
        return new String(chars);   // the data will be copied several times along the way :-(  FIX IT!
    }

    static String encode(final byte[] bytes) {
        return encode(bytes, bytes.length);
    }

    static byte[] decode(final String string) {
        // TODO consider using a char array instead of charAt
        final int byteCount = string.charAt(0), pairCount = (byteCount >> 1) + 1;
        final byte[] bytes = new byte[byteCount];
        int i = 1, j = 0;
        for (; i < pairCount; i++) {
            final char ch = string.charAt(i);
            bytes[j++] = (byte) ((ch >>> 8) & 0xFF);
            bytes[j++] = (byte) (ch & 0xFF);
        }
        if (j < byteCount) {
            bytes[j] = (byte) string.charAt(i);
        }
        return bytes;
    }

/*
    public static void main(String[] args) {
        System.out.println(2.0 - 1.1);
    }
*/

    public static void main(String[] args) {
        if (args.length > 0) {
            final int length = Integer.parseInt(args[0]);
            System.out.println("length = " + length);
            final byte[] buffer91 = new byte[length * 2];
            final byte[] dec91decoded = new byte[length];
            for (int j = length; j < length + 1; j++) {
                for (int i = 0; i < 1; i++) {

                    final byte[] bytes = randomByteArray(j);
                    final String str2in1 = encode(bytes);
                    final byte[] decoded = decode(str2in1);

                    final char[] b64enc = Base64Coder.encode(bytes);
                    final byte[] b64dec = Base64Coder.decode(b64enc);

                    final int count91 = Base91.encode(bytes, bytes.length, buffer91);

                    int outDecodeCount = Base91.decode(buffer91, count91, dec91decoded);
                    System.out.println("outDecodeCount = " + outDecodeCount);
                    final boolean same1 = java.util.Arrays.equals(bytes, dec91decoded);
                    System.out.println("same1 = " + same1);


                    System.out.println("orig bytes count = " + bytes.length);
                    System.out.println("b64enc = " + b64enc.length);
                    System.out.println("str2in1 = " + str2in1.length());
                    System.out.println(1.0 / ((double) b64enc.length / str2in1.length()));

                    System.out.println("count91 = " + count91);

                    System.out.println("trying to UTF-8 encode");

                    byte[] bytes2in1 = str2in1.getBytes(StandardCharsets.UTF_8);
                    byte[] bytes64b = String.valueOf(b64enc).getBytes(StandardCharsets.UTF_8);
                    System.out.println("bytes2in1 = " + bytes2in1.length);
                    System.out.println("bytes64b  = " + bytes64b.length);
                    String str91 = new String(buffer91, 0, count91);
                    System.out.println("bytes91 = " + str91.getBytes(StandardCharsets.UTF_8).length);

//            System.out.println("bytes   = " + java.util.Arrays.toString(bytes));
//            System.out.println("decoded = " + java.util.Arrays.toString(decoded));
                    final boolean same = java.util.Arrays.equals(bytes, decoded);
                    if (!java.util.Arrays.equals(bytes, b64dec)) {
                        System.out.println("KWAS");
                    }
                    if (!same) {
                        System.out.println("bytes == decoded -> " + same);
                        return;
                    }

                }
            }
            final byte[] bytes = randomByteArray(length);
//            System.out.println(Base64Coder.encode(bytes).length);
            System.out.println(CharCoder.encode(bytes).length());
            long start = System.currentTimeMillis();
            for (int i = 0; i < LOOP; i++) {
                final char[] b64enc = Base64Coder.encode(bytes);
                final byte[] b64dec = Base64Coder.decode(b64enc);
            }
            long end = System.currentTimeMillis();
            System.out.println("time b64 = " + (end - start));
            start = System.currentTimeMillis();
            for (int i = 0; i < LOOP; i++) {

                final String string = encode(bytes);
                final byte[] decoded = decode(string);

            }
            end = System.currentTimeMillis();
            System.out.println("time 2in1 = " + (end - start));

            start = System.currentTimeMillis();
            for (int i = 0; i < LOOP; i++) {
                final int count91 = Base91.encode(bytes, bytes.length, buffer91);
                final int decCount = Base91.decode(buffer91, count91, dec91decoded);
            }
            end = System.currentTimeMillis();
            System.out.println("time base91 = " + (end - start));
        }
    }

    private static byte[] randomByteArray(final int length) {
        final Random random = new Random(System.currentTimeMillis());
        final byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = (byte) random.nextInt(256);
        }
        return array;
    }
}
