package com.sejong.ghostyattendance.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseRequest {

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
