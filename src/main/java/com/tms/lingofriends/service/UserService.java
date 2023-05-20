package com.tms.lingofriends.service;

import com.tms.lingofriends.model.User;
import com.tms.lingofriends.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    public Optional<User> findUserByUserName(String name) {
        return userRepository.findUserByUserName(name);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }
  /* @Transactional
   public void addCourseToUser(int userId, int courseId) {

       userRepository.addCourseToUser(userId, courseId);
   }*/
}