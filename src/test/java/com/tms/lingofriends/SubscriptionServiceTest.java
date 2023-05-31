package com.tms.lingofriends;

import com.tms.lingofriends.mapper.SubscriptionToSubscriptionResponseMapper;
import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.model.response.SubscriptionResponse;
import com.tms.lingofriends.repository.SubscriptionRepository;
import com.tms.lingofriends.security.Authorization;
import com.tms.lingofriends.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private SubscriptionToSubscriptionResponseMapper subscriptionToSubscriptionResponseMapper;
    @Mock
    private Authorization authorization;
    private int id;
    private Subscription subscription;
    private com.tms.lingofriends.model.response.SubscriptionResponse subscriptionResponse;
    private final List<Subscription> subscriptions = new ArrayList<>();
    private final List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

    @BeforeEach
    public void init() {
        id = 1;
        subscription = new Subscription();
        subscription.setId(1);
        subscription.setExpireDate(LocalDate.now());
        subscription.setStatus(true);
        subscription.setUserId(1);
        subscription.setUserLogin("UserLoginTeste");
        subscriptions.add(subscription);
        subscriptionResponse = new SubscriptionResponse();
        subscriptionResponses.add(subscriptionResponse);
    }

    @Test
    public void getSubscriptionByIdTest() {
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));
        when(subscriptionToSubscriptionResponseMapper.subscriptionToResponse(subscription)).thenReturn(subscriptionResponse);
        SubscriptionResponse returned = subscriptionService.getSubscriptionResponseById(id);
        verify(subscriptionRepository).findById(id);
        verify(subscriptionToSubscriptionResponseMapper).subscriptionToResponse(subscription);
        assertEquals(subscriptionResponse, returned);
    }

    @Test
    public void findSubscriptionResponseByUserIdTest() {
        when(subscriptionRepository.findSubscriptionByUserId(subscription.getUserId())).thenReturn(subscriptions);
        when(subscriptionToSubscriptionResponseMapper.subscriptionToResponse(subscription)).thenReturn(subscriptionResponse);
        assertEquals(subscriptionResponses, subscriptionService.findSubscriptionResponseByUserId(subscription.getUserId()));
    }

    @Test
    public void createServiceTest() {
        subscription.setExpireDate(LocalDate.now());
        subscription.setStatus(true);
        subscriptionService.createSubscription(subscription);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    public void updateServiceTest() {
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));
        when(authorization.authorization(subscription.getUserLogin())).thenReturn(true);
        subscriptionService.updateSubscription(subscription);
        verify(subscriptionRepository).saveAndFlush(subscription);
    }

    @Test
    public void deleteSubscriptionTest() {
        when(subscriptionRepository.findById(id)).thenReturn(Optional.of(subscription));
        when(authorization.authorization(subscription.getUserLogin())).thenReturn(true);
        subscriptionService.deleteSubscriptionById(id);
        verify(subscriptionRepository).deleteSubscriptionById(id);
    }
}