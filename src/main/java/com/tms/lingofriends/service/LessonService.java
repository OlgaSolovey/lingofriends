package com.tms.lingofriends.service;

import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LessonService {
    LessonRepository lessonRepository;
    @Autowired
    public LessonService(LessonRepository lessonRepository){
        this.lessonRepository=lessonRepository;
    }
    public ArrayList<Lesson> getAllLesson() {
        return (ArrayList<Lesson>) lessonRepository.findAll();
    }

    public Lesson getLessonById(int id) {
        return lessonRepository.findById(id).orElse(new Lesson());
    }

    public Optional<Lesson> findLessonByTitle(String title) {
        return lessonRepository.findLessonByTitle(title);
    }
    public Optional<ArrayList<Lesson>> findLessonByCourseId(Integer courseId) {
        return lessonRepository.findLessonByCourseId(courseId);
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
