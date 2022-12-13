package com.sejong.ghostyattendance.lecture.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LecturesOfCoursesRes {

    private String course_name;
    private String course_id;
    private String class_id;
    private UnpassCountRes unpass_count;
    private List<LectureRes> lectures;
}
