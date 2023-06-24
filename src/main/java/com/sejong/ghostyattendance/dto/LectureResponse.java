package com.sejong.ghostyattendance.dto;

import com.sejong.ghostyattendance.domain.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureResponse {

    private String location;
    private String lecture_name;
    private String start_date;
    private String end_date;
    private Long progress;
    private String is_pass;
    private int status;

    public static LectureResponse from(Lecture lecture) {
        return new LectureResponse(
                lecture.getLocation(),
                lecture.getLectureDateAndName().getLectureName(),
                lecture.getLectureDateAndName().getStartDate(),
                lecture.getLectureDateAndName().getEndDate(),
                lecture.getProgress(),
                lecture.getIsPass(),
                lecture.getStatus()
        );
    }
}
