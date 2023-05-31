package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.model.response.SubscriptionResponse;
import com.tms.lingofriends.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Get information about  all subscription  for admin.")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Subscription>> getAllSubscription() {
        List<Subscription> subscriptionsList = subscriptionService.getAllSubscription();
        return new ResponseEntity<>(subscriptionsList, (!subscriptionsList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about subscription by id for admin.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable int id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        if (subscription == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @Operation(summary = "Get information about subscription by id for user.")
    @GetMapping("/res/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionResponseById(@PathVariable int id) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionResponseById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get information about subscription by  user id for user.")
    @GetMapping("/us/{userId}")
    public ResponseEntity<List<SubscriptionResponse>> findSubscriptionResponseByUserId(@PathVariable Integer userId) {
        List<SubscriptionResponse> list = subscriptionService.findSubscriptionResponseByUserId(userId);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createSubscription(@RequestBody Subscription subscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
            }
        }
        subscriptionService.createSubscription(subscription);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateSubscription(@RequestBody Subscription subscription) {
        subscriptionService.updateCourse(subscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSubscriptionById(@PathVariable int id) {
        subscriptionService.deleteSubscriptionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}