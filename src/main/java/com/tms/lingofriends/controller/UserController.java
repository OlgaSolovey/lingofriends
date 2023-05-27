package com.tms.lingofriends.controller;

import com.tms.lingofriends.exception.BadReqException;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tms.lingofriends.util.ExceptionMesseges.NOT_CREATED;
import static com.tms.lingofriends.util.ExceptionMesseges.NOT_UPDATE;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // admin user all
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // user user all
    @GetMapping("/res")
    public ResponseEntity<List<UserResponse>> getAllUserResponse() {
        List<UserResponse> userList = userService.getAllUsersResponse();
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    // admin user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // user user by id
    @GetMapping("/res/{id}")
    public ResponseEntity<UserResponse> getUserResponseById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserResponseById(id), HttpStatus.OK);
    }

    //  user by name
    @GetMapping("/name/res/{name}")
    public ResponseEntity<UserResponse> findUserResponseByUserName(@PathVariable String name) {
        return new ResponseEntity<>(userService.findUserResponseByUserName(name), HttpStatus.OK);
    }

    //  user by lang name
    @GetMapping("/res/ln/{languageName}")
    public ResponseEntity<List<UserResponse>> findUserResponseByLanguageName(@PathVariable String languageName) {
        List<UserResponse> list = userService.findUserResponseByLanguageName(languageName);
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new BadReqException(NOT_CREATED);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new BadReqException(NOT_UPDATE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 /*  @PostMapping("/addcourse")
    public ResponseEntity<HttpStatus> addCourseToUser(@RequestParam int userId, @RequestParam int courseId) {
        userService.addCourseToUser(userId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}