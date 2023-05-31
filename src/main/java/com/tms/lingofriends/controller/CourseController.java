package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get information about all courses for admin.")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Course>> getAllCourse() {
        List<Course> courseList = courseService.getAllCourse();
        return new ResponseEntity<>(courseList, (!courseList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about all courses for user. ")
    @GetMapping("/res/all")
    public ResponseEntity<List<CourseResponse>> getAllCoursesResponse() {
        List<CourseResponse> coursesList = courseService.getAllCoursesResponse();
        return new ResponseEntity<>(coursesList, (!coursesList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about course by id for admin.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @Operation(summary = "Get information about course by id for user. ")
    @GetMapping("/res/{id}")
    public ResponseEntity<CourseResponse> getCourseResponseById(@PathVariable int id) {
        return new ResponseEntity<>(courseService.getCourseResponseById(id), HttpStatus.OK);
    }

    @Operation(summary = " Get information about course by language name for user.")
    @GetMapping("/res/ln/{languageName}")
    public ResponseEntity<List<CourseResponse>> findCourseResponseByLanguageName(@PathVariable String languageName) {
        List<CourseResponse> list = courseService.findCourseResponseByLanguageName(languageName);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about course by  user id for admin.")
    @GetMapping("/admin/us/{userId}")
    public ResponseEntity<List<Course>> findCourseByUserId(@PathVariable Integer userId) {
        List<Course> list = courseService.findCourseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about course by user id for user.")
    @GetMapping("/res/us/{userId}")
    public ResponseEntity<List<CourseResponse>> findCourseResponseByUserId(@PathVariable Integer userId) {
        List<CourseResponse> list = courseService.findCourseResponseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}