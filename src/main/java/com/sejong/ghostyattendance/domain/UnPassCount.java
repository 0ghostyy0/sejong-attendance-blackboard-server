package com.sejong.ghostyattendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnPassCount {

    private final int thisWeekUnPassCount;
    private final int allUnPassCount;

}
