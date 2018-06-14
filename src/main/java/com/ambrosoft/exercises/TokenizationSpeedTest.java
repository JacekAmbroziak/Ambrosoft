package com.ambrosoft.exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacek R. Ambroziak
 */
final class TokenizationSpeedTest {
    public static void main(String[] args) {
        try {
            final List<String> allLines = new ArrayList<>();
            for (final File file : Common.textFilesInDir("/usr/src")) {
                try {
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        allLines.add(line);
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("e = " + e);
                }
            }





        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
