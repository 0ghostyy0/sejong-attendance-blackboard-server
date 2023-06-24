package com.sejong.ghostyattendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRes {

    private String location;
    private String lecture_name;
    private String start_date;
    private String end_date;
    private Long progress;
    private String is_pass;
    private int status;
}
