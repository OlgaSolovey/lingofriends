package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.User;
import com.tms.lingofriends.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUser() {
        ArrayList<User> list = userService.getAllUsers();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<User> findUserByUserName(@PathVariable String name) {
        Optional<User> user = userService.findUserByUserName(name);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.NOT_FOUND)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));

    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  /*  @PostMapping("/coursetouser")
    public ResponseEntity<HttpStatus> addCourseToUser(@RequestParam int userId, @RequestParam int courseId) {
        userService.addCourseToUser(userId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}