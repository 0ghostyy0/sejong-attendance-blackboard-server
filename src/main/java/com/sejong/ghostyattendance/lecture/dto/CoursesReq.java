package com.sejong.ghostyattendance.lecture.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CoursesReq {

    @NotBlank
    private List<Course> courses;
}
