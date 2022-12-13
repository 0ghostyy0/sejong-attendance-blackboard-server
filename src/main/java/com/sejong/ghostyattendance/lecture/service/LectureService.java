package com.sejong.ghostyattendance.lecture.service;

import com.sejong.ghostyattendance.lecture.dto.Course;
import com.sejong.ghostyattendance.lecture.dto.CoursesReq;
import com.sejong.ghostyattendance.lecture.dto.LectureRes;
import com.sejong.ghostyattendance.lecture.dto.LecturesOfCoursesRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.html.HtmlReadOptions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static tech.tablesaw.api.ColumnType.INTEGER;
import static tech.tablesaw.api.ColumnType.STRING;

@Service
public class LectureService {

    private final String blackboardUrl;

    public LectureService(@Value("${blackboard_url}") String blackboardUrl) {
        this.blackboardUrl = blackboardUrl;
    }

    public List<LecturesOfCoursesRes> getLectures(CoursesReq courses) {

        LecturesOfCoursesRes lecturesOfCourses = new LecturesOfCoursesRes();
        List<LecturesOfCoursesRes> allLecturesOfCourses = new ArrayList<>();

        try {
            for (Course course : courses.getCourses()) {
                lecturesOfCourses.setLectures(parseLectures(course));

                allLecturesOfCourses.add(lecturesOfCourses);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return allLecturesOfCourses;
    }

    private List<LectureRes> parseLectures(Course course) throws IOException {
        ColumnType[] types = {STRING, STRING, STRING, STRING, STRING, STRING, INTEGER, STRING};
        String semester = "20222020";
        String location = String.format(blackboardUrl, course.getStudent_id(), semester, course.getDept_id(),
                course.getCourse_id(), course.getClass_id(), course.getStudent_id());
        Table table = Table.read().usingOptions(HtmlReadOptions.builder(new URL(location))
                .tableName("lectures")
                .columnTypes(types));
        List<LectureRes> lectures = new ArrayList<>();
        LectureRes lectureRes;

        //System.out.println(table.structure());
        //System.out.println(table);

        for (Row row : table) {
            System.out.println(row);
            List<String> parsingLectureName = parseLectureName(row.getString("lecture_name"));
            String startDate = parsingLectureName.get(0);
            String endDate = parsingLectureName.get(1);
            String onlyLectureName = parsingLectureName.get(2);

            lectureRes = new LectureRes();
            lectureRes.setLocation(row.getString("location"));
            lectureRes.setLecture_name(onlyLectureName);
            lectureRes.setStart_date(startDate);
            lectureRes.setEnd_date(endDate);
            lectureRes.setProgress(row.getInt("progress"));
            if (row.getString("is_pass").equals("P")) {
                lectureRes.setIs_pass("P");
            } else {
                lectureRes.setIs_pass("F");
            }

            lectures.add(lectureRes);
        }

        return lectures;
    }

    private List<String> parseLectureName(String lectureName) {
        int length = lectureName.length();
        String fullDate = lectureName.substring(length - 35, length);
        String startDate = fullDate.substring(2, 4) + fullDate.substring(5, 7) + fullDate.substring(8, 10)
                + fullDate.substring(11, 13) + fullDate.substring(14, 16);
        String endDate = fullDate.substring(21, 23) + fullDate.substring(24, 26) + fullDate.substring(27, 29)
                + fullDate.substring(30, 32) + fullDate.substring(33, 35);
        String onlyLectureName = lectureName.substring(6, length - 38);

        return List.of(startDate, endDate, onlyLectureName);
    }

}
