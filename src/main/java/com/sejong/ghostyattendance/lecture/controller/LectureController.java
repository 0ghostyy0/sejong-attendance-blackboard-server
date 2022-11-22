package com.sejong.ghostyattendance.lecture.controller;

import com.sejong.ghostyattendance.lecture.dto.CourseReq;
import com.sejong.ghostyattendance.lecture.dto.LecturesOfCoursesRes;
import com.sejong.ghostyattendance.lecture.service.LectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<List<LecturesOfCoursesRes>> getLectures(@RequestBody List<CourseReq> courses) {
        List<LecturesOfCoursesRes> lecturesOfCourses = lectureService.getLectures(courses);

        return ResponseEntity.ok(lecturesOfCourses);
    }

}
