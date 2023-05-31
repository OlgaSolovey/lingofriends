package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.AccessException;
import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.CourseToCourseResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.ACCESS_IS_DENIED;
import static com.tms.lingofriends.util.ExceptionMesseges.COURSES_NOT_FOUND;
import static com.tms.lingofriends.util.ExceptionMesseges.COURSE_NOT_FOUND;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseToCourseResponseMapper courseToCourseResponseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseToCourseResponseMapper courseToCourseResponseMapper) {
        this.courseRepository = courseRepository;
        this.courseToCourseResponseMapper = courseToCourseResponseMapper;
    }

    public List<Course> getAllCourse() {
        List<Course> courses = courseRepository.findAll();
        if (!courses.isEmpty()) {
            return courses;
        } else {
            throw new NotFoundException(COURSES_NOT_FOUND);
        }
    }

    public List<CourseResponse> getAllCoursesResponse() throws NotFoundException {
        List<CourseResponse> courses = courseRepository.findAll().stream()
                .filter(course -> !course.isDeleted())
                .map(courseToCourseResponseMapper::courseToResponse)
                .collect(Collectors.toList());
        if (!courses.isEmpty()) {
            return courses;
        } else throw new NotFoundException(COURSES_NOT_FOUND);
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException(COURSE_NOT_FOUND));
    }

    public CourseResponse getCourseResponseById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isEmpty()) {
            return courseToCourseResponseMapper.courseToResponse(course.get());
        } else throw new NotFoundException(COURSE_NOT_FOUND);
    }

    public List<CourseResponse> findCourseResponseByLanguageName(String languageName) {
        List<CourseResponse> courses = courseRepository.findCourseByLanguageName(languageName).stream()
                .filter(course -> !course.isDeleted())
                .map(courseToCourseResponseMapper::courseToResponse)
                .collect(Collectors.toList());
        if (!courses.isEmpty()) {
            return courses;
        } else {
            throw new NotFoundException(COURSES_NOT_FOUND);
        }
    }

    public List<Course> findCourseByUserId(Integer userId) {
        List<Course> courses = courseRepository.findCourseByUserId(userId);
        if (!courses.isEmpty()) {
            return courses;
        } else {
            throw new NotFoundException(COURSES_NOT_FOUND);
        }
    }

    public List<CourseResponse> findCourseResponseByUserId(int userId) {
        List<CourseResponse> courses = courseRepository.findCourseByUserId(userId).stream()
                .filter(course -> !course.isDeleted())
                .map(courseToCourseResponseMapper::courseToResponse)
                .collect(Collectors.toList());
        if (!courses.isEmpty()) {
            return courses;
        } else {
            throw new NotFoundException(COURSES_NOT_FOUND);
        }
    }

    public Course createCourse(Course course) {
        course.setCreated(new Timestamp(System.currentTimeMillis()));
        course.setChanged(new Timestamp(System.currentTimeMillis()));
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        Course courses = courseRepository.findById(course.getId()).get();
        if (authorization(courses.getUserLogin())) {
            return courseRepository.saveAndFlush(course);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public void deleteCourseById(int id) {
        if (authorization(getUserLogin(id))) {
            courseRepository.deleteCourseById(id);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    private String getUserLogin(int id) {
        return courseRepository.findById(id).get().getUserLogin();
    }

    public boolean authorization(String login) {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName().equals(login);
    }
}