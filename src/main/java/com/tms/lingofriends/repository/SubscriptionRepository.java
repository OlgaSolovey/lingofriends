package com.tms.lingofriends.repository;

import com.tms.lingofriends.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

}