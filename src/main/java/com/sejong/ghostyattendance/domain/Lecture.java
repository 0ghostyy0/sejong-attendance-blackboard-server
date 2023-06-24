package com.sejong.ghostyattendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Lecture {

    private static final String PASS_SYMBOL = "P";
    private static final String UNPASS_SYMBOL = "F";

    private final LectureDateAndName lectureDateAndName;
    private final String location;
    private final Long progress;
    private final String isPass;
    private final int status;

    public static Lecture of(
            LectureDateAndName lectureDateAndName,
            String location,
            String progress,
            String isPass,
            int status
    ) {
        lectureDateAndName.formatDate();
        return new Lecture(
                lectureDateAndName,
                location,
                Math.round(Double.parseDouble(progress)),
                convertIsPass(isPass),
                status
        );
    }

    private static String convertIsPass(String isPassString) {
        if (PASS_SYMBOL.equals(isPassString)) {
            return PASS_SYMBOL;
        }
        return UNPASS_SYMBOL;
    }

    // 현재 수강 기간이지만 아직 수강하지 않은 경우
    public boolean isCanTakeThisWeekButNot() {
        return status == 3;
    }

    // 수강 기간이 지났는데 수강하지 않은 경우
    public boolean isCanNotTakeButNot() {
        return status == 4;
    }
}
