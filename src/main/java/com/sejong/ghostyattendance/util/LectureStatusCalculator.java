package com.sejong.ghostyattendance.util;

public class LectureStatusCalculator {

    private static final String PASS_SYMBOL = "P";
    private static final String UNPASS_SYMBOL = "F";

    public static int calculate(String startDate, String endDate, String currentDate, String isPass) {
        /* 상태 1 : 아직 기간 안됨
        상태 2 : 들음
        상태 3 : 지금 수강기간, 안들음
        상태 4 : 수강기간 지남, 안들음 */

        if (Long.parseLong(currentDate) < Long.parseLong(startDate)) {
            return 1;
        } else if (PASS_SYMBOL.equals(isPass)) {
            return 2;
        } else if (Long.parseLong(currentDate) <= Long.parseLong(endDate) && UNPASS_SYMBOL.equals(isPass)) {
            return 3;
        } else {
            return 4;
        }
    }
}
