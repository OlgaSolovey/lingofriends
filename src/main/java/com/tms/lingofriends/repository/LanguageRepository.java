package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

    @Transactional
    @Modifying
    //  @Query(value = "UPDATE user_table SET is_deleted=true WHERE id=?1", nativeQuery = true)
    @Query(value = "update Language l set l.isDeleted=true where l.id=:id")
    int deleteLanguage(int id);
}