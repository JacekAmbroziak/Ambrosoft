package com.ambrosoft.exercises;

/*
 * basE91 encoding/decoding routines
 *
 * Copyright (c) 2000-2006 Joachim Henke
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of Joachim Henke nor the names of his contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Hand optimized by yours truly, JRA
 */

public final class Base91 {
    private static final byte[] ENC_TAB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()*+,./:;<=>?@[]^_`{|}~\"".getBytes();
    private static final byte[] DEC_TAB = new byte[256];

    static {
        for (int i = 255; i >= 0; --i) {
            DEC_TAB[i] = -1;
        }
        for (int i = 90; i >= 0; --i) {
            DEC_TAB[ENC_TAB[i]] = (byte) i;
        }
    }

    public static int encode(final byte[] inBuf, final int length, final byte[] outBuf) {
        int ebq = 0, en = 0, count = 0;
        for (int i = 0; i < length; ) {
            ebq |= (inBuf[i++] & 255) << en;
            en += 8;
            if (en > 13) {
                int ev;
                if ((ev = ebq & 8191) > 88) {
                    ebq >>= 13;
                    en -= 13;
                } else {
                    ev = ebq & 16383;
                    ebq >>= 14;
                    en -= 14;
                }
                {
                    final int div = ev / 91;
                    outBuf[count + 1] = ENC_TAB[div];
                    outBuf[count] = ENC_TAB[ev - 91 * div];  // modulo
                    count += 2;
                }
            }
        }
        if (en > 0) {
            outBuf[count++] = ENC_TAB[ebq % 91];
            if (en > 7 || ebq > 90) {
                outBuf[count++] = ENC_TAB[ebq / 91];
            }
        }
        return count;
    }

    public static int encode(final byte[] inBuf, final int length, final char[] outBuf) {
        int ebq = 0, en = 0, count = 0;
        for (int i = 0; i < length; ) {
            ebq |= (inBuf[i++] & 255) << en;
            en += 8;
            if (en > 13) {
                int ev;
                if ((ev = ebq & 8191) > 88) {
                    ebq >>= 13;
                    en -= 13;
                } else {
                    ev = ebq & 16383;
                    ebq >>= 14;
                    en -= 14;
                }
                {
                    final int div = ev / 91;
                    outBuf[count + 1] = (char) ENC_TAB[div];
                    outBuf[count] = (char) ENC_TAB[ev - 91 * div];  // modulo
                    count += 2;
                }
            }
        }
        if (en > 0) {
            outBuf[count++] = (char) ENC_TAB[ebq % 91];
            if (en > 7 || ebq > 90) {
                outBuf[count++] = (char) ENC_TAB[ebq / 91];
            }
        }
        return count;
    }

    public static int decode(final byte[] encoded, final int length, final byte[] decoded) {
        int count = 0, dv = -1, dbq = 0, dn = 0;
        for (int i = 0; i < length; ++i) {
            final byte db = DEC_TAB[encoded[i]];
            if (db != -1) {
                if (dv == -1) {
                    dv = db;
                } else {
                    dv += db * 91;
                    dbq |= dv << dn;
                    dn += (dv & 8191) > 88 ? 13 : 14;
                    do {
                        decoded[count++] = (byte) dbq;
                        dbq >>= 8;
                        dn -= 8;
                    } while (dn > 7);
                    dv = -1;
                }
            }
        }
        if (dv != -1) {
            decoded[count++] = (byte) (dbq | dv << dn);
        }
        return count;
    }

    public static String encode(final byte[] bytes, final int count) {
        final byte[] encoded = new byte[count + count / 2 + 2];   // TODO reuse
        final int encodedCount = encode(bytes, count, encoded);
        return new String(encoded, 0, encodedCount);
    }

    public static String encode(final byte[] bytes) {
        return encode(bytes, bytes.length);
    }

    static byte[] decode(final String string) {
        byte[] buffer = string.getBytes();
        final int length = buffer.length;
        final byte[] decoded = new byte[length];
        final int count = decode(buffer, length, decoded);
        buffer = new byte[count];   // reuse var + GC prev value
        System.arraycopy(decoded, 0, buffer, 0, count);
        return buffer;
    }
}
