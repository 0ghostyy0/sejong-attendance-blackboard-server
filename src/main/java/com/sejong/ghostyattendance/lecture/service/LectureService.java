package com.sejong.ghostyattendance.lecture.service;

import com.sejong.ghostyattendance.lecture.dto.CourseReq;
import com.sejong.ghostyattendance.lecture.dto.LecturesOfCoursesRes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {

    public List<LecturesOfCoursesRes> getLectures(List<CourseReq> courses) {

        LecturesOfCoursesRes lectures;
        List<LecturesOfCoursesRes> lecturesOfCourses = new ArrayList<>();
        String currentDate;

        for (CourseReq course: courses) {

        }

        return lecturesOfCourses;
    }
}
