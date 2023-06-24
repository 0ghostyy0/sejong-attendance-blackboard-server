package com.sejong.ghostyattendance.util;

import com.sejong.ghostyattendance.domain.Lecture;
import com.sejong.ghostyattendance.domain.UnPassCount;
import java.util.List;

public class LectureUnPassCounter {

    public static UnPassCount count(List<Lecture> lectures) {
        int thisWeekUnPassCount = 0;
        int allUnPassCount = 0;

        if (lectures.size() == 0) {
            return new UnPassCount(0, 0);
        }

        for (Lecture lecture : lectures) {
            if (lecture.isCanTakeThisWeekButNot()) {
                thisWeekUnPassCount++;
                allUnPassCount++;
            }
            if (lecture.isCanNotTakeButNot()) {
                allUnPassCount++;
            }
        }

        return new UnPassCount(thisWeekUnPassCount, allUnPassCount);
    }

}
