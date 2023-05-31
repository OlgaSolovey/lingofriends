package com.tms.lingofriends.controller;

import com.tms.lingofriends.mapper.LessonToLessonResponseMapper;
import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.LessonResponse;
import com.tms.lingofriends.service.LessonService;
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
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final LessonToLessonResponseMapper lessonToLessonResponseMapper;

    @Autowired
    public LessonController(LessonService lessonService, LessonToLessonResponseMapper lessonToLessonResponseMapper) {
        this.lessonService = lessonService;
        this.lessonToLessonResponseMapper = lessonToLessonResponseMapper;
    }

    @Operation(summary = "Get information about all lessons for admin.")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Lesson>> getAllLesson() {
        List<Lesson> lessonList = lessonService.getAllLesson();
        return new ResponseEntity<>(lessonList, (!lessonList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about all lessons for user.")
    @GetMapping("/res/all")
    public ResponseEntity<List<LessonResponse>> getAllLessonResponse() {
        List<LessonResponse> lessonsList = lessonService.getAllLessonResponse();
        return new ResponseEntity<>(lessonsList, (!lessonsList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about lesson by id for admin.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }

    @Operation(summary = "Get information about lesson by id for user.")
    @GetMapping("/res/{id}")
    public ResponseEntity<LessonResponse> getLessonResponseById(@PathVariable int id) {
        return new ResponseEntity<>(lessonService.getLessonResponseById(id), HttpStatus.OK);
    }

    @Operation(summary = " Get information about lesson by course id for admin.")
    @GetMapping("/admin/cr/{courseId}")
    public ResponseEntity<List<Lesson>> findLessonByCourseId(@PathVariable Integer courseId) {
        List<Lesson> list = lessonService.findLessonByCourseId(courseId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = " Get information about lesson by course id for user.")
    @GetMapping("/res/cr/{courseId}")
    public ResponseEntity<List<LessonResponse>> findLessonResponseByCourseId(@PathVariable Integer courseId) {
        List<LessonResponse> list = lessonService.findLessonResponseByCourseId(courseId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = " Get information about lesson by user id for user.")
    @GetMapping("/res/us/{userId}")
    public ResponseEntity<List<LessonResponse>> findLessonResponseByUserId(@PathVariable Integer userId) {
        List<LessonResponse> list = lessonService.findLessonResponseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createLesson(@RequestBody Lesson lesson) {
        lessonService.createLesson(lesson);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public void updateLesson(@RequestBody Lesson lesson) {
        lessonService.updateLesson(lesson);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLessonById(@PathVariable int id) {
        lessonService.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}