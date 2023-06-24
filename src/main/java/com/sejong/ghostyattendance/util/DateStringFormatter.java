package com.sejong.ghostyattendance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateStringFormatter {

    private static final String DATE_TIME_FORMAT = "yyMMddhhmm";

    public static String now() {
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.of(curDate, curTime);

        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
