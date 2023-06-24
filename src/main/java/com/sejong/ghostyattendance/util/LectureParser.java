package com.sejong.ghostyattendance.util;

import static java.util.stream.Collectors.toList;
import static tech.tablesaw.api.ColumnType.STRING;

import com.sejong.ghostyattendance.domain.Course;
import com.sejong.ghostyattendance.domain.Lecture;
import com.sejong.ghostyattendance.domain.LectureDateAndName;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.html.HtmlReadOptions;

public class LectureParser {

    private static final String LECTURE_NAME_PREFIX = "XIN";

    public static List<Lecture> parse(Course course, String blackboardUrl, String semester) throws IOException {
        ColumnType[] types = {STRING, STRING, STRING, STRING, STRING, STRING, STRING, STRING};
        String location = makeLocation(course, blackboardUrl, semester);

        Table table = Table.read().usingOptions(HtmlReadOptions.builder(new URL(location))
                .tableName("lectures")
                .columnTypes(types));

        return table.stream()
                .map(row -> {
                    LectureDateAndName lectureDateAndName = parseLectureName(row.getString("lecture_name"));
                    String startDate = lectureDateAndName.getStartDate();
                    String endDate = lectureDateAndName.getEndDate();
                    String currentDate = DateStringFormatter.now();
                    int status = LectureStatusCalculator.calculate(
                            startDate,
                            endDate,
                            currentDate,
                            row.getString("is_pass")
                    );

                    return Lecture.of(
                            lectureDateAndName,
                            row.getString("location"),
                            row.getString("progress"),
                            row.getString("is_pass"),
                            status
                    );

                }).collect(toList());
    }

    private static String makeLocation(Course course, String blackboardUrl, String semester) {
        String currentYear = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        return String.format(
                blackboardUrl,
                course.getStudentId(),
                currentYear,
                semester,
                course.getDeptId(),
                course.getCourseId(),
                course.getClassId(),
                course.getStudentId()
        );
    }

    private static LectureDateAndName parseLectureName(String lectureName) {
        int length = lectureName.length();

        String fullDate = lectureName.substring(length - 35, length);
        String startDate = fullDate.substring(2, 4) + fullDate.substring(5, 7) + fullDate.substring(8, 10)
                + fullDate.substring(11, 13) + fullDate.substring(14, 16);
        String endDate = fullDate.substring(21, 23) + fullDate.substring(24, 26) + fullDate.substring(27, 29)
                + fullDate.substring(30, 32) + fullDate.substring(33, 35);
        String onlyLectureName = parserLectureName(lectureName, length);

        return new LectureDateAndName(startDate, endDate, onlyLectureName);
    }

    private static String parserLectureName(String lectureName, int length) {
        if (length - 38 < 6 || !lectureName.contains(LECTURE_NAME_PREFIX)) {
            return lectureName.substring(0, length - 38);
        }
        return lectureName.substring(6, length - 38);
    }
}
