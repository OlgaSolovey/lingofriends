package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.AccessException;
import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.LessonToLessonResponseMapper;
import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.LessonResponse;
import com.tms.lingofriends.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.ACCESS_IS_DENIED;
import static com.tms.lingofriends.util.ExceptionMesseges.LESSON_NOT_FOUND;
import static com.tms.lingofriends.util.ExceptionMesseges.LESSONS_NOT_FOUND;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonToLessonResponseMapper lessonToLessonResponseMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonToLessonResponseMapper lessonToLessonResponseMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonToLessonResponseMapper = lessonToLessonResponseMapper;
    }

    public List<Lesson> getAllLesson() {
        List<Lesson> lessons = lessonRepository.findAll();
        if (!lessons.isEmpty()) {
            return lessons;
        } else {
            throw new NotFoundException(LESSONS_NOT_FOUND);
        }
    }

    public List<LessonResponse> getAllLessonResponse() throws NotFoundException {
        List<LessonResponse> lessons = lessonRepository.findAll().stream()
                .filter(lesson -> !lesson.isDeleted())
                .map(lessonToLessonResponseMapper::lessonToResponse)
                .collect(Collectors.toList());
        if (!lessons.isEmpty()) {
            return lessons;
        } else throw new NotFoundException(LESSONS_NOT_FOUND);
    }

    public Lesson getLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() ->
                new NotFoundException(LESSON_NOT_FOUND));
    }

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

    public List<LessonResponse> findLessonResponseByUserId(int userId) {
        List<LessonResponse> lessons = lessonRepository.findLessonByUserId(userId).stream()
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
        lesson.setCreated(new Timestamp(System.currentTimeMillis()));
        lesson.setChanged(new Timestamp(System.currentTimeMillis()));
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(Lesson lesson) {
        Lesson lessons = lessonRepository.findById(lesson.getId()).get();
        if (authorization(lesson.getUserLogin())) {
            return lessonRepository.saveAndFlush(lesson);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public void deleteLessonById(int id) {
        if (authorization(getUserLogin(id))) {
            lessonRepository.deleteLessonById(id);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    private String getUserLogin(int id) {
        return lessonRepository.findById(id).get().getUserLogin();
    }

    public boolean authorization(String login) {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName().equals(login);
    }
}