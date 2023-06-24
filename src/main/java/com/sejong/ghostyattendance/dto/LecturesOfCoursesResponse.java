package com.sejong.ghostyattendance.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LecturesOfCoursesResponse {

    private String course_name;
    private String course_id;
    private String class_id;
    private UnPassCountResponse unpass_count;
    private List<LectureResponse> lectures;
}
