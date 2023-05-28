package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.SubscriptionToSubscriptionResponseMapper;
import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.model.response.SubscriptionResponse;
import com.tms.lingofriends.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.SUBSCRIPTION_NOT_FOUND;
import static com.tms.lingofriends.util.ExceptionMesseges.SUBSCRIPTIONS_NOT_FOUND;


@Service
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;
    SubscriptionToSubscriptionResponseMapper subscriptionToSubscriptionResponseMapper;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,SubscriptionToSubscriptionResponseMapper subscriptionToSubscriptionResponseMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionToSubscriptionResponseMapper = subscriptionToSubscriptionResponseMapper;
    }
    public List<Subscription> getAllSubscription() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (!subscriptions.isEmpty()) {
            return subscriptions;
        } else {
            throw new NotFoundException(SUBSCRIPTIONS_NOT_FOUND);
        }
    }
    public Subscription getSubscriptionById(int id) {
        return subscriptionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(SUBSCRIPTION_NOT_FOUND));
    }
    public SubscriptionResponse getSubscriptionResponseById(int id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (!subscription.isEmpty()) {
            return subscriptionToSubscriptionResponseMapper.subscriptionToResponse(subscription.get());
        } else throw new NotFoundException(SUBSCRIPTION_NOT_FOUND);
    }
    public List<SubscriptionResponse> findSubscriptionResponseByUserId(int userId) {
        List<SubscriptionResponse> subscriptions = subscriptionRepository.findSubscriptionByUserId(userId).stream()
                .map(subscriptionToSubscriptionResponseMapper::subscriptionToResponse)
                .collect(Collectors.toList());
        if (!subscriptions.isEmpty()) {
            return subscriptions;
        } else {
            throw new NotFoundException(SUBSCRIPTION_NOT_FOUND);
        }
    }
    public Subscription createSubscription(Subscription subscription) {
        subscription.setExpireDate(LocalDate.now().plusYears(1));
        subscription.setStatus(true);
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