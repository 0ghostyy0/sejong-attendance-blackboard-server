package com.sejong.ghostyattendance.service;

import static tech.tablesaw.api.ColumnType.STRING;

import com.sejong.ghostyattendance.exception.ParsingException;
import com.sejong.ghostyattendance.domain.Course;
import com.sejong.ghostyattendance.dto.CoursesReq;
import com.sejong.ghostyattendance.dto.LectureRes;
import com.sejong.ghostyattendance.dto.LecturesOfCoursesRes;
import com.sejong.ghostyattendance.dto.UnpassCountRes;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.html.HtmlReadOptions;

@Service
public class LectureService {

    private final String blackboardUrl;
    private final String semester;

    public LectureService(@Value("${blackboard_url}") String blackboardUrl, @Value("${semester}") String semester) {
        this.blackboardUrl = blackboardUrl;
        this.semester = semester;
    }

    public List<LecturesOfCoursesRes> getLectures(CoursesReq courses) {
        LecturesOfCoursesRes lecturesOfCourses;
        List<LecturesOfCoursesRes> allLecturesOfCourses = new ArrayList<>();
        List<LectureRes> lectures;
        List<Integer> statusCounter;
        UnpassCountRes unpassCountRes;

        try {
            for (Course course : courses.getCourses()) {
                lecturesOfCourses = new LecturesOfCoursesRes();
                lectures = parseLectures(course);
                statusCounter = checkStatusCounter(lectures);
                unpassCountRes = new UnpassCountRes();
                unpassCountRes.setThis_week(statusCounter.get(0));
                unpassCountRes.setAll(statusCounter.get(1));

                lecturesOfCourses.setLectures(lectures);
                lecturesOfCourses.setCourse_name(course.getCourse_name());
                lecturesOfCourses.setCourse_id(course.getCourse_id());
                lecturesOfCourses.setClass_id(course.getClass_id());
                lecturesOfCourses.setUnpass_count(unpassCountRes);

                allLecturesOfCourses.add(lecturesOfCourses);
            }
        } catch (IOException exception) {
            throw new ParsingException("과목 정보를 가져오는데 실패했습니다.");
        }

        return allLecturesOfCourses;
    }

    private List<LectureRes> parseLectures(Course course) throws IOException {
        ColumnType[] types = {STRING, STRING, STRING, STRING, STRING, STRING, STRING, STRING};
        String currentYear = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String location = String.format(blackboardUrl, course.getStudent_id(), currentYear, semester, course.getDept_id(),
                course.getCourse_id(), course.getClass_id(), course.getStudent_id());

        Table table = Table.read().usingOptions(HtmlReadOptions.builder(new URL(location))
                .tableName("lectures")
                .columnTypes(types));

        List<LectureRes> lectures = new ArrayList<>();
        LectureRes lectureRes;

        for (Row row : table) {
            List<String> parsingLectureName = parseLectureName(row.getString("lecture_name"));
            String startDate = parsingLectureName.get(0);
            String endDate = parsingLectureName.get(1);
            String onlyLectureName = parsingLectureName.get(2);
            String currentDate = getCurrentDate();
            int status = parseLectureStatus(startDate, endDate, currentDate, row.getString("is_pass"));

            lectureRes = new LectureRes();
            lectureRes.setLocation(row.getString("location"));
            lectureRes.setLecture_name(onlyLectureName);
            lectureRes.setStart_date(startDate.substring(2));
            lectureRes.setEnd_date(endDate.substring(2));
            lectureRes.setProgress(Math.round(Double.parseDouble(row.getString("progress"))));
            if (row.getString("is_pass").equals("P")) {
                lectureRes.setIs_pass("P");
            } else {
                lectureRes.setIs_pass("F");
            }
            lectureRes.setStatus(status);

            lectures.add(lectureRes);
        }

        return lectures;
    }

    private List<String> parseLectureName(String lectureName) {
        String startDate = null;
        String endDate = null;
        String onlyLectureName = null;
        int length = lectureName.length();
        try {
            String fullDate = lectureName.substring(length - 35, length);
            startDate = fullDate.substring(2, 4) + fullDate.substring(5, 7) + fullDate.substring(8, 10)
                    + fullDate.substring(11, 13) + fullDate.substring(14, 16);
            endDate = fullDate.substring(21, 23) + fullDate.substring(24, 26) + fullDate.substring(27, 29)
                    + fullDate.substring(30, 32) + fullDate.substring(33, 35);
            onlyLectureName = parserLectureName(lectureName, length);
        } catch (Exception e) {
            System.out.println(lectureName);
            System.out.println(e.getMessage());

        }

        return List.of(startDate, endDate, onlyLectureName);
    }
    private static String parserLectureName(String lectureName, int length) {
        if (length - 38 < 6 || !lectureName.contains("XIN")) {
            return lectureName.substring(0, length - 38);
        }
        else {
            return lectureName.substring(6, length - 38);
        }
    }

    private int parseLectureStatus(String startDate, String endDate, String currentDate, String isPass) {
        /* 상태 1 : 아직 기간 안됨
        상태 2 : 들음
        상태 3 : 지금 수강기간, 안들음
        상태 4 : 수강기간 지남, 안들음 */

        if (Long.parseLong(currentDate) < Long.parseLong(startDate)) {
            return 1;
        } else if (isPass.equals("P")) {
            return 2;
        } else if (Long.parseLong(currentDate) <= Long.parseLong(endDate) && isPass.equals("F")) {
            return 3;
        } else {
            return 4;
        }
    }

    private String getCurrentDate() {
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.of(curDate, curTime);

        return dateTime.format(DateTimeFormatter.ofPattern("yyMMddhhmm"));
    }

    private List<Integer> checkStatusCounter(List<LectureRes> lectures) {
        Integer thisWeekUnpassCount = 0;
        Integer allUnpassCount = 0;

        if (lectures.size() != 0) {
            for (LectureRes lecture: lectures) {
                if (lecture.getStatus() == 3) {
                    thisWeekUnpassCount++;
                    allUnpassCount++;
                }
                if (lecture.getStatus() == 4) {
                    allUnpassCount++;
                }
            }
        }

        return List.of(thisWeekUnpassCount, allUnpassCount);
    }
}