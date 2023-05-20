package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findCourseByTitle(String title);

    Optional<ArrayList<Course>> findCourseByLanguageName(String languageName);

    Optional<ArrayList<Course>> findCourseByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE course_table SET is_deleted = true WHERE id = :id")
    void deleteCourseById(int id);
}