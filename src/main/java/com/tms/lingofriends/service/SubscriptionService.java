package com.tms.lingofriends.service;

import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

      /*  public ArrayList<User> getAllUsers() {
            return (ArrayList<User>) userRepository.findAll();
        }*/

    public Subscription getSubscriptionById(int id) {
        return subscriptionRepository.findById(id).orElse(new Subscription());
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateCourse(Subscription subscription) {
        return subscriptionRepository.saveAndFlush(subscription);
    }
   /*public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }*/
}
