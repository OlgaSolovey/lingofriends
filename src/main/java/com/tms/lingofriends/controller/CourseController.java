package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Course;

import com.tms.lingofriends.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Course>> getAllCourse() {
        ArrayList<Course> list = courseService.getAllCourse();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Course> findCourseByTitle(@PathVariable String title) {
        Optional<Course> course = courseService.findCourseByTitle(title);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.NOT_FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));

    }

    @GetMapping("/ln/{languageName}")
    public ResponseEntity<ArrayList<Course>> findCourseByLanguageName(@PathVariable String languageName) {
        Optional<ArrayList<Course>> courses = courseService.findCourseByLanguageName(languageName);
        if (courses.isPresent()) {
            return new ResponseEntity<>(courses.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/us/{userId}")
    public ResponseEntity<ArrayList<Course>> findCourseByUserId(@PathVariable Integer userId) {
        Optional<ArrayList<Course>> courses = courseService.findCourseByUserId(userId);
        if (courses.isPresent()) {
            return new ResponseEntity<>(courses.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
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