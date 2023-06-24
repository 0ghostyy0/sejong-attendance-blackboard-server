package com.sejong.ghostyattendance.controller;

import com.sejong.ghostyattendance.dto.CoursesRequest;
import com.sejong.ghostyattendance.dto.LecturesOfCoursesResponse;
import com.sejong.ghostyattendance.service.LectureService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping
    public ResponseEntity<List<LecturesOfCoursesResponse>> getLectures(@RequestBody CoursesRequest request) {
        List<LecturesOfCoursesResponse> lecturesOfCourses = lectureService.getLectures(request);
        return ResponseEntity.ok(lecturesOfCourses);
    }
}
