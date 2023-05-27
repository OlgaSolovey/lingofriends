package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.LessonToLessonResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.model.response.LessonResponse;
import com.tms.lingofriends.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.*;

@Service
public class LessonService {
    LessonRepository lessonRepository;
    LessonToLessonResponseMapper lessonToLessonResponseMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonToLessonResponseMapper lessonToLessonResponseMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonToLessonResponseMapper = lessonToLessonResponseMapper;
    }

    // for admin get all lesson
    public List<Lesson> getAllLesson() {
        List<Lesson> lessons = lessonRepository.findAll();
        if (!lessons.isEmpty()) {
            return lessons;
        } else {
            throw new NotFoundException(LESSONS_NOT_FOUND);
        }
    }
    // for user get all lesson
    public List<LessonResponse> getAllLessonResponse() throws NotFoundException {
        List<LessonResponse> lessons = lessonRepository.findAll().stream()
                .filter(lesson -> !lesson.isDeleted())
                .map(lessonToLessonResponseMapper::lessonToResponse)
                .collect(Collectors.toList());
        if (!lessons.isEmpty()) {
            return lessons;
        } else throw new NotFoundException(LESSONS_NOT_FOUND);
    }

    // for admin get by id
    public Lesson getLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException(LESSON_NOT_FOUND ));
    }
    // for user get by id
    public LessonResponse getLessonResponseById(int id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isEmpty()) {
            return lessonToLessonResponseMapper.lessonToResponse(lesson.get());
        } else throw new NotFoundException(LESSON_NOT_FOUND);
    }

    public List<Lesson> findLessonByCourseId(Integer courseId) {
        List<Lesson> lessons = lessonRepository.findLessonByCourseId(courseId);
        if (!lessons.isEmpty()) {
            return lessons;
        } else {
            throw new NotFoundException(LESSONS_NOT_FOUND);
        }
    }

    // for user by course id
    public List<LessonResponse> findLessonResponseByCourseId(int courseId) {
        List<LessonResponse> lessons = lessonRepository.findLessonByCourseId(courseId).stream()
                .filter(lesson -> !lesson.isDeleted())
                .map(lessonToLessonResponseMapper::lessonToResponse)
                .collect(Collectors.toList());
        if (!lessons.isEmpty()) {
            return lessons;
        } else {
            throw new NotFoundException(LESSON_NOT_FOUND);
        }
    }

    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(Lesson lesson) {
        return lessonRepository.saveAndFlush(lesson);
    }

    @Transactional
    public void deleteLessonById(int id) {
        lessonRepository.deleteLessonById(id);
    }

}