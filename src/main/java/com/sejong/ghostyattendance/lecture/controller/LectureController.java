package com.sejong.ghostyattendance.lecture.controller;

import com.sejong.ghostyattendance.lecture.dto.CoursesReq;
import com.sejong.ghostyattendance.lecture.dto.LecturesOfCoursesRes;
import com.sejong.ghostyattendance.lecture.service.LectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<List<LecturesOfCoursesRes>> getLectures(@RequestBody CoursesReq courses) {
        log.info(log.getName());
        List<LecturesOfCoursesRes> lecturesOfCourses = lectureService.getLectures(courses);

        return ResponseEntity.ok(lecturesOfCourses);
    }

}
