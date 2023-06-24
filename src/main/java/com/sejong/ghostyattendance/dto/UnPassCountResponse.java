package com.sejong.ghostyattendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnPassCountResponse {

    private int this_week;
    private int all;
}
