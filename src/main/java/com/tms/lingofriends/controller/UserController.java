package com.tms.lingofriends.controller;

import com.tms.lingofriends.exception.BadReqException;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.PasswordRequest;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.tms.lingofriends.util.ExceptionMessages.BAD_PASSWORD;
import static com.tms.lingofriends.util.ExceptionMessages.NOT_CREATED;
import static com.tms.lingofriends.util.ExceptionMessages.NOT_UPDATE;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get information about all users for admin.")
    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about all users for user.")
    @GetMapping("/res/all")
    public ResponseEntity<List<UserResponse>> getAllUserResponse() {
        List<UserResponse> userList = userService.getAllUsersResponse();
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get information about user by id for admin.")
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get information about user by id for user.")
    @GetMapping("/res/{id}")
    public ResponseEntity<UserResponse> getUserResponseById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserResponseById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get information about user by user name for user.")
    @GetMapping("/res/name/{name}")
    public ResponseEntity<UserResponse> findUserResponseByUserName(@PathVariable String name) {
        return new ResponseEntity<>(userService.findUserResponseByUserName(name), HttpStatus.OK);
    }

    @Operation(summary = "Get information about user by language name for user.")
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

    @Operation(summary = "Add favorite course to user.")
    @PostMapping("/addcourse")
    public ResponseEntity<HttpStatus> addCourseToUser(@RequestParam int userId, @RequestParam int courseId) {
        userService.addCourseToUser(userId, courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get favorite course for user.")
    @GetMapping("/getCourse/{userId}")
    public ResponseEntity<List<Course>> giveAllCourseForThisUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getCourseForUser(userId), HttpStatus.OK);
    }

    @PutMapping("/changePass")
    public ResponseEntity<HttpStatus> changeUserPassword(@RequestBody @Valid PasswordRequest request, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.changeUserPassword(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new BadReqException(BAD_PASSWORD);
        }
    }
}