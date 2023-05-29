package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUserName(String name);

    List<User> findUserByLanguageName(String languageName);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE user_table SET is_deleted = true WHERE id = :id")
    void deleteUserById(int id);

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO l_user_course_table (id, user_id, course_id) VALUES (DEFAULT, :userId, :courseId)")
    void addCourseToUser(Integer userId, Integer courseId);

    @Modifying
    @Query(nativeQuery = true,
            value = "SELECT c.id,c.user_id,c.title,c.language_name,c.description,c.created,c.changed,c.is_deleted" +
                    " FROM l_user_course_table JOIN course_table as c ON l_user_course_table.course_id = c.id WHERE l_user_course_table.user_id=:userId")
    List<Course> getCourseForUser(int userId);
}