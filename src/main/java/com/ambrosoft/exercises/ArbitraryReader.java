package com.ambrosoft.exercises;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 1/19/17.
 */
public class ArbitraryReader {
    private final int BUFSIZE = 4096;
    private final BufferedReader br;
    final byte[] temp = new byte[BUFSIZE];
    int remaining;
    int start;
    boolean isEmpty;

    ArbitraryReader(InputStream is) {
        br = new BufferedReader(is);
    }

    static class BufferedReader {
        private final InputStream is;
        private boolean closed;

        BufferedReader(InputStream is) {
            this.is = is;
        }

        int read4k(final byte[] buffer) throws IOException {
            if (closed) {
                return -1;
            } else {
                final int count = is.read(buffer);
                if (count < 0) {
                    is.close();
                    closed = true;
                    return -1;
                } else {
                    return count;
                }
            }
        }
    }

    int readNBad(byte[] buffer, int n) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException();
        }
        if (n < 0 || n > buffer.length) {
            throw new IllegalArgumentException();
        }
        int index = 0, nRead = 0;
        int lastN, k;
        while ((lastN = br.read4k(temp)) > 0) {
            k = Math.min(lastN, n - nRead);
            System.out.println("lastN = " + lastN);
            System.out.println("k = " + k);
            System.arraycopy(temp, 0, buffer, index, k);
            if (k == 0) {
                break;
            }
            index += k;
            nRead += k;
        }
        return nRead;
    }

    // works but doesn't copy more than a bufferful at a time
    int readN2(byte[] buffer, int n) throws IOException {
        if (remaining > 0) {
            final int k = Math.min(remaining, n);
            System.arraycopy(temp, start, buffer, 0, k);
            remaining -= k;
            start += k;
            return k;
        } else {
            final int count = br.read4k(temp);
            if (count < 0) {
                return -1;
            } else {
                start = 0;
                remaining = count;
                return readN(buffer, n);
            }
        }
    }

    // try to copy up to n bytes from internal buffer to arg buffer, starting from pos
    private int copyFromBuffer(byte[] buffer, int n, int pos) {
        if (remaining > 0) {
            final int toCopy = Math.min(remaining, n);
            System.arraycopy(temp, start, buffer, pos, toCopy);
            remaining -= toCopy;
            start += toCopy;
            return toCopy;
        } else {
            return 0;
        }
    }

    private boolean fillBuffer() throws IOException {
        if (isEmpty) {
            return false;
        } else {
            final int count = br.read4k(temp);
            if (count > 0) {
                remaining = count;
                start = 0;
                return true;
            } else {
                isEmpty = true;
                remaining = 0;
                return false;
            }
        }
    }

    int readN(byte[] buffer, int n) throws IOException {
        int copied = copyFromBuffer(buffer, n, 0);
        if (copied == n) {
            return copied;
        } else if (isEmpty) {
            return copied;
        } else {
            int toRead = n - copied;
            int count = copied;
            while (toRead > 0 && fillBuffer()) {
                copied = copyFromBuffer(buffer, toRead, count);
                toRead -= copied;
                count += copied;
            }
            return count;
        }
    }

    static void testOneShot(String fileName) throws IOException {
        File file = new File(fileName);
        int fileSize = (int) file.length();
        final ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        int n = localRandom.nextInt(1, fileSize + 1);
//        n = 12307;
//        n = 14002;
        System.out.println("n = " + n);
        {
            InputStream is = new FileInputStream(fileName);
            ArbitraryReader ar = new ArbitraryReader(is);
            OutputStream os = new FileOutputStream("/Users/jacek/Downloads/k123_good.txt");

            byte[] buffer = new byte[fileSize];
            int nRead = ar.readN(buffer, n);
            is.close();
            os.write(buffer, 0, nRead);
            os.close();

        }
        {
            InputStream is = new FileInputStream(fileName);
            ArbitraryReader ar = new ArbitraryReader(is);
            OutputStream os = new FileOutputStream("/Users/jacek/Downloads/k123_bad.txt");

            byte[] buffer = new byte[fileSize];
            int nRead = ar.readNBad(buffer, n);
            is.close();
            os.write(buffer, 0, nRead);
            os.close();
        }
    }

    public static void main(String[] args) throws IOException {
        final String fileName = "/Users/jacek/Downloads/Utilities2016.txt";
        InputStream is = new FileInputStream(fileName);
        ArbitraryReader ar = new ArbitraryReader(is);
        {
            OutputStream os = new FileOutputStream("/Users/jacek/Downloads/k123.txt");
            byte[] buffer = new byte[4096 * 2];
            int nRead;
            final ThreadLocalRandom localRandom = ThreadLocalRandom.current();
            while ((nRead = ar.readN(buffer, localRandom.nextInt(1, buffer.length))) > 0) {
                os.write(buffer, 0, nRead);
            }
            os.close();
        }

        testOneShot(fileName);
    }
}
