package com.sejong.ghostyattendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureDateAndName {

    private String startDate;
    private String endDate;
    private final String lectureName;

    public void formatDate() {
        startDate = startDate.substring(2);
        endDate = endDate.substring(2);
    }
}
