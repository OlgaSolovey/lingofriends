package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<ArrayList<User>> findUserByLanguageId(String languageId);


    @Transactional
    @Modifying
    //  @Query(value = "UPDATE user_table SET is_deleted=true WHERE id=?1", nativeQuery = true)
    @Query(value = "update User u set u.isDeleted=true where u.id=:id")
    int deleteUser(int id);
   /* @Transactional
    @Modifying
    //  @Query(value = "UPDATE user_table SET is_deleted=true WHERE id=?1", nativeQuery = true)
    @Query(value = "update User u set u.subscription=id where u.id=:id")
    int addSubscriptionToUser(int id);*/
}