package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.EducationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EducationProductRepository extends JpaRepository<EducationProduct, Integer> {
    Optional<ArrayList<EducationProduct>> findEducationProductByLanguageId(String languageId);
    Optional<ArrayList<EducationProduct>> findEducationProductByUserId(String UserId);
    @Transactional
    @Modifying
    // @Query(value = "UPDATE educstion_product_table SET is_deleted=true WHERE id=?1", nativeQuery = true)
    @Query(value = "update EducationProduct e set e.isDeleted=true where e.id=:id")
    int deleteEducationProduct(int id);
}