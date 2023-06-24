package com.sejong.ghostyattendance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Course {

    @NotBlank
    private String student_id;

    @NotBlank
    private String dept_id;

    @NotBlank
    private String course_name;

    @NotBlank
    private String course_id;

    @NotBlank
    private String class_id;
}
