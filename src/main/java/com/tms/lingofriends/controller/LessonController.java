package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.model.response.LessonResponse;
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
import java.util.List;


@RestController
@RequestMapping("/lesson")
public class LessonController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // admin  all lesson
    @GetMapping
    public ResponseEntity<List<Lesson>> getAllLesson() {
        List<Lesson> lessonList = lessonService.getAllLesson();
        return new ResponseEntity<>(lessonList, (!lessonList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // user  all lesson
    @GetMapping("/res")
    public ResponseEntity<List<LessonResponse>> getAllLessonResponse() {
        List<LessonResponse> lessonsList = lessonService.getAllLessonResponse();
        return new ResponseEntity<>(lessonsList, (!lessonsList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @GetMapping("/res/{id}")
    public ResponseEntity<LessonResponse> getLessonResponseById(@PathVariable int id) {
        return new ResponseEntity<>(lessonService.getLessonResponseById(id), HttpStatus.OK);
    }

    @GetMapping("/cr/{courseId}")
    public ResponseEntity<List<Lesson>> findLessonByCourseId(@PathVariable Integer courseId) {

        List<Lesson> list = lessonService.findLessonByCourseId(courseId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // for user by course id
    @GetMapping("/res/cr/{courseId}")
    public ResponseEntity<List<LessonResponse>> findLessonResponseByCourseId(@PathVariable Integer courseId) {
        List<LessonResponse> list = lessonService.findLessonResponseByCourseId(courseId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
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