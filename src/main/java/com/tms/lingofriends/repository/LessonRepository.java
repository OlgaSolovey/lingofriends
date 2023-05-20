package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    Optional<Lesson> findLessonByTitle(String title);
    Optional<ArrayList<Lesson>> findLessonByCourseId(Integer courseId);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value="UPDATE lesson_table SET is_deleted= true WHERE id=:id")
    void deleteLessonById(int id);

}
