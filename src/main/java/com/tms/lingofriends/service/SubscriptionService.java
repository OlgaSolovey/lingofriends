package com.tms.lingofriends.service;

import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription getSubscriptionById(int id) {
        return subscriptionRepository.findById(id).orElse(new Subscription());
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateCourse(Subscription subscription) {
        return subscriptionRepository.saveAndFlush(subscription);
    }
    @Transactional
    public void deleteSubscriptionById(int id) {
        subscriptionRepository.deleteSubscriptionById(id);
    }
}