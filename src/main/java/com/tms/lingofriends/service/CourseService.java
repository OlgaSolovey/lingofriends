package com.tms.lingofriends.service;

import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CourseService {
    CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ArrayList<Course> getAllCourse() {
        return (ArrayList<Course>) courseRepository.findAll();
    }

    public Optional<ArrayList<Course>> findCourseByLanguageName(String languageName) {
        return courseRepository.findCourseByLanguageName(languageName);
    }

    public Optional<ArrayList<Course>> findCourseByUserId(Integer userId) {
        return courseRepository.findCourseByUserId(userId);
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(new Course());
    }

    public Optional<Course> findCourseByTitle(String title) {
        return courseRepository.findCourseByTitle(title);
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