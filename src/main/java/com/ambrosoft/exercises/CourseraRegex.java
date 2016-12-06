package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jacek on 11/25/16.
 */
public class CourseraRegex {

    static List<String> getTokens(String text, String pattern) {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    static void test(String text, String pattern) {
        List<String> tokens = getTokens(text, pattern);
        System.out.println("tokens = " + tokens.toString());
    }

    public static void main(String[] args) {
        test("Splitting a string, it's easy as 1 2 33! Right?", "[0-9]+");
    }
}
