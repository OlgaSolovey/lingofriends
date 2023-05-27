package com.tms.lingofriends.repository;


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
  /* @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO l_users_course (id, user_id, course_id) VALUES (DEFAULT, :userId, :courseId)",
            countQuery = "SELECT * FROM l_users_course WHERE user_id = :userId, course_id=:courseId")
    void addCourseToUser(int userId, int courseId);*/
}