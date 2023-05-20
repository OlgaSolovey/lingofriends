package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.service.LessonService;
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
@RequestMapping("/lesson")
public class LessonController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Lesson>> getAllLesson() {
        ArrayList<Lesson> list = lessonService.getAllLesson();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Lesson> findLessonByTitle(@PathVariable String title) {
        Optional<Lesson> lesson = lessonService.findLessonByTitle(title);
        return lesson.map(value -> new ResponseEntity<>(value, HttpStatus.NOT_FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));

    }

    @GetMapping("/cr/{courseId}")
    public ResponseEntity<ArrayList<Lesson>> findLessonByCourseId(@PathVariable Integer courseId) {
        Optional<ArrayList<Lesson>> lesson = lessonService.findLessonByCourseId(courseId);
        return lesson.map(value -> new ResponseEntity<>(value, HttpStatus.NOT_FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));

    }

    @PostMapping
    public ResponseEntity<HttpStatus> createLesson(@RequestBody Lesson lesson, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        lessonService.createLesson(lesson);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateLesson(@RequestBody Lesson lesson) {
        lessonService.updateLesson(lesson);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLessonById(@PathVariable int id) {
        lessonService.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}