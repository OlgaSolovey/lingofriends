package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.UserToUserResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.USERS_NOT_FOUND;
import static com.tms.lingofriends.util.ExceptionMesseges.USER_NOT_FOUND;

@Service
public class UserService {
    UserRepository userRepository;
    UserToUserResponseMapper userToUserResponseMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserToUserResponseMapper userToUserResponseMapper) {
        this.userRepository = userRepository;
        this.userToUserResponseMapper = userToUserResponseMapper;
    }

    // !for admin get all user
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }

    // !for user get all user
    public List<UserResponse> getAllUsersResponse() throws NotFoundException {
        List<UserResponse> users = userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .map(userToUserResponseMapper::userToResponse)
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users;
        } else throw new NotFoundException(USERS_NOT_FOUND);
    }

    // !for admin get by id
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));
    }

    // !for user get by id
    public UserResponse getUserResponseById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isEmpty()) {
            return userToUserResponseMapper.userToResponse(user.get());
        } else throw new NotFoundException(USER_NOT_FOUND);
    }

    // !for user findUserByUserName
    public UserResponse findUserResponseByUserName(String name) {
        Optional<User> user = userRepository.findUserByUserName(name);
        if (!user.isEmpty()) {
            return userToUserResponseMapper.userToResponse(user.get());
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }

    // !for user findUserBy lang
    public List<UserResponse> findUserResponseByLanguageName(String languageName) {
        List<UserResponse> users = userRepository.findUserByLanguageName(languageName).stream()
                .filter(user -> !user.isDeleted())
                .map(userToUserResponseMapper::userToResponse)
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }

    public User createUser(User user) {
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        user.setDeleted(false);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }
  @Transactional
   public void addCourseToUser(int userId, int courseId) {
       userRepository.addCourseToUser(userId, courseId);
   }
   @Transactional
    public List<Course> getCourseForUser(int userId) {
        List<Course> courses = userRepository.getCourseForUser(userId);
        if (!courses.isEmpty()) {
            return courses;
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }
}