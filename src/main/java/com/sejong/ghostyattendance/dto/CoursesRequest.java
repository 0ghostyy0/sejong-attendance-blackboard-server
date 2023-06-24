package com.sejong.ghostyattendance.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoursesRequest {

    @NotBlank
    private List<CourseRequest> courses;
}
