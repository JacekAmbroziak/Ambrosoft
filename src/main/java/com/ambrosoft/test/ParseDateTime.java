package com.ambrosoft.test;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.time.Instant;

/**
 * Created on 3/26/18
 */
public class ParseDateTime {
    private static final DateTimeFormatter DATE_TIME_FORMAT = ISODateTimeFormat.dateTimeParser();

    public static void main(String[] args) {
        final String text = "2018-03-26T14:30:10.800Z";
        System.out.println("instant = " + Instant.parse(text).toEpochMilli());
        System.out.println("joda    = " + DATE_TIME_FORMAT.parseDateTime(text).getMillis());
    }
}
