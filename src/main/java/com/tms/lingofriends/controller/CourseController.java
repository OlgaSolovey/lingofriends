package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Course;

import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // for admin  all course
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourse() {
        List<Course> courseList = courseService.getAllCourse();
        return new ResponseEntity<>(courseList, (!courseList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // for user  all course
    @GetMapping("/res")
    public ResponseEntity<List<CourseResponse>> getAllCoursesResponse() {
        List<CourseResponse> coursesList = courseService.getAllCoursesResponse();
        return new ResponseEntity<>(coursesList, (!coursesList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // for admin course by id
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // for user course by id
    @GetMapping("/res/{id}")
    public ResponseEntity<CourseResponse> getCourseResponseById(@PathVariable int id) {
        return new ResponseEntity<>(courseService.getCourseResponseById(id), HttpStatus.OK);
    }

    // for admin by language
    @GetMapping("/res/ln/{languageName}")
    public ResponseEntity<List<CourseResponse>> findCourseResponseByLanguageName(@PathVariable String languageName) {
        List<CourseResponse> list = courseService.findCourseResponseByLanguageName(languageName);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    // for admin by user id
    @GetMapping("/us/{userId}")
    public ResponseEntity<List<Course>> findCourseByUserId(@PathVariable Integer userId) {

        List<Course> list = courseService.findCourseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // for user by user id
    @GetMapping("/res/us/{userId}")
    public ResponseEntity<List<CourseResponse>> findCourseResponseByUserId(@PathVariable Integer userId) {
        List<CourseResponse> list = courseService.findCourseResponseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCourse(@RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        courseService.createCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 /* @PostMapping("/edtocourse")
  public ResponseEntity<HttpStatus> addEdProductToCourse(@RequestParam int courseId, @RequestParam int edprodId) {
      courseService.addEdProductToCourse(courseId, edprodId);
      return new ResponseEntity<>(HttpStatus.OK);
  }*/
}