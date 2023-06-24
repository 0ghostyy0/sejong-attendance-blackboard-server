package com.sejong.ghostyattendance.service;

import static java.util.stream.Collectors.toList;

import com.sejong.ghostyattendance.domain.Course;
import com.sejong.ghostyattendance.domain.Lecture;
import com.sejong.ghostyattendance.domain.UnPassCount;
import com.sejong.ghostyattendance.dto.CourseRequest;
import com.sejong.ghostyattendance.dto.CoursesRequest;
import com.sejong.ghostyattendance.dto.LectureResponse;
import com.sejong.ghostyattendance.dto.LecturesOfCoursesResponse;
import com.sejong.ghostyattendance.dto.UnPassCountResponse;
import com.sejong.ghostyattendance.exception.ParsingException;
import com.sejong.ghostyattendance.util.LectureParser;
import com.sejong.ghostyattendance.util.LectureUnPassCounter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final String blackboardUrl;
    private final String semester;

    public LectureService(@Value("${blackboard_url}") String blackboardUrl, @Value("${semester}") String semester) {
        this.blackboardUrl = blackboardUrl;
        this.semester = semester;
    }

    public List<LecturesOfCoursesResponse> getLectures(CoursesRequest request) {
        try {
            List<LecturesOfCoursesResponse> allLecturesOfCourses = new ArrayList<>();
            for (CourseRequest courseRequest : request.getCourses()) {
                Course course = toDomain(courseRequest);
                List<Lecture> lectures = LectureParser.parse(course, blackboardUrl, semester);
                UnPassCount unpassCount = LectureUnPassCounter.count(lectures);

                LecturesOfCoursesResponse lecturesOfCoursesResponse = makeResponse(
                        unpassCount,
                        course,
                        lectures
                );
                allLecturesOfCourses.add(lecturesOfCoursesResponse);
            }
            return allLecturesOfCourses;
        } catch (IOException exception) {
            throw new ParsingException("과목 정보를 가져오는데 실패했습니다.");
        }
    }

    private Course toDomain(CourseRequest request) {
        return new Course(
                request.getStudent_id(),
                request.getDept_id(),
                request.getCourse_name(),
                request.getCourse_id(),
                request.getClass_id()
        );
    }

    private LecturesOfCoursesResponse makeResponse(
            UnPassCount unpassCount,
            Course course,
            List<Lecture> lectures
    ) {
        UnPassCountResponse unPassCountResponse = new UnPassCountResponse(
                unpassCount.getThisWeekUnPassCount(),
                unpassCount.getAllUnPassCount()
        );
        return new LecturesOfCoursesResponse(
                course.getCourseName(),
                course.getCourseId(),
                course.getClassId(),
                unPassCountResponse,
                lectures.stream()
                        .map(LectureResponse::from)
                        .collect(toList())
        );
    }
}
