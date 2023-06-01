package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findSubscriptionByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE subscription_table SET is_deleted = true WHERE id = :id")
    void deleteSubscriptionById(int id);
}