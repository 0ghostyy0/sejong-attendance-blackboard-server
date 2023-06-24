package com.sejong.ghostyattendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Course {

    private final String studentId;
    private final String deptId;
    private final String courseName;
    private final String courseId;
    private final String classId;
}
