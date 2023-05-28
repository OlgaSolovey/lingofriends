package com.tms.lingofriends.mapper;

import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.model.response.SubscriptionResponse;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionToSubscriptionResponseMapper {
    public SubscriptionResponse subscriptionToResponse(Subscription subscription) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        subscriptionResponse.setExpireDate(subscriptionResponse.getExpireDate());
        subscriptionResponse.setStatus(subscription.getStatus());

        return subscriptionResponse;
    }
}