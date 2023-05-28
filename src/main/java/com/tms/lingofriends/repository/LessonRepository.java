package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findLessonByCourseId(Integer courseId);

    List<Lesson> findLessonByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE lesson_table SET is_deleted= true WHERE id=:id")
    void deleteLessonById(int id);
}