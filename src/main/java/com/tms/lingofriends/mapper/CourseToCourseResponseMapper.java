package com.tms.lingofriends.mapper;

import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.response.CourseResponse;
import org.springframework.stereotype.Component;

@Component
public class CourseToCourseResponseMapper {
    public CourseResponse courseToResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setTitle(course.getTitle());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setLanguageName(course.getLanguageName());
        return courseResponse;
    }
}