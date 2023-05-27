package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.CourseToCourseResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.*;

@Service
public class CourseService {
    CourseRepository courseRepository;
    CourseToCourseResponseMapper courseToCourseResponseMapper;

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

    // for user get all course
    public List<CourseResponse> getAllCoursesResponse() throws NotFoundException {
        List<CourseResponse> courses = courseRepository.findAll().stream()
                .filter(course -> !course.isDeleted())
                .map(courseToCourseResponseMapper::courseToResponse)
                .collect(Collectors.toList());
        if (!courses.isEmpty()) {
            return courses;
        } else throw new NotFoundException(COURSES_NOT_FOUND);
    }

    // for admin course by id
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new NotFoundException(COURSE_NOT_FOUND));
    }

    // for user course by id
    public CourseResponse getCourseResponseById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isEmpty()) {
            return courseToCourseResponseMapper.courseToResponse(course.get());
        } else throw new NotFoundException(COURSE_NOT_FOUND);
    }

    // for user by language
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

    // for admin by user id
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
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepository.saveAndFlush(course);
    }

    @Transactional
    public void deleteCourseById(int id) {
        courseRepository.deleteCourseById(id);
    }
}