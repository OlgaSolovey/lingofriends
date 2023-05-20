package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Subscription;
import com.tms.lingofriends.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

   /* @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUser() {
        ArrayList<User> list = userService.getAllUsers();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable int id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        if (subscription == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createSubscription(@RequestBody Subscription subscription, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        subscriptionService.createSubscription(subscription);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateSubscription(@RequestBody Subscription subscription) {
        subscriptionService.updateCourse(subscription);
    }

  /*  @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}