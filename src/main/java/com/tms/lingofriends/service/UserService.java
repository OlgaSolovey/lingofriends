package com.tms.lingofriends.service;

import com.tms.lingofriends.exception.AccessException;
import com.tms.lingofriends.exception.BadReqException;
import com.tms.lingofriends.exception.NotFoundException;
import com.tms.lingofriends.mapper.UserToUserResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.PasswordRequest;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.repository.UserRepository;
import com.tms.lingofriends.security.Authorization;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tms.lingofriends.util.ExceptionMesseges.ACCESS_IS_DENIED;
import static com.tms.lingofriends.util.ExceptionMesseges.BAD_PASSWORD;
import static com.tms.lingofriends.util.ExceptionMesseges.USERS_NOT_FOUND;
import static com.tms.lingofriends.util.ExceptionMesseges.USER_NOT_FOUND;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserToUserResponseMapper userToUserResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private  final Authorization authorization;


    @Autowired
    public UserService(UserRepository userRepository, UserToUserResponseMapper userToUserResponseMapper, PasswordEncoder passwordEncoder, Authorization authorization) {
        this.userRepository = userRepository;
        this.userToUserResponseMapper = userToUserResponseMapper;
        this.passwordEncoder = passwordEncoder;
        this.authorization = authorization;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }

    public List<UserResponse> getAllUsersResponse() throws NotFoundException {
        List<UserResponse> users = userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .map(userToUserResponseMapper::userToResponse)
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users;
        } else throw new NotFoundException(USERS_NOT_FOUND);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));
    }

    public UserResponse getUserResponseById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isEmpty()) {
            return userToUserResponseMapper.userToResponse(user.get());
        } else throw new NotFoundException(USER_NOT_FOUND);
    }

    public UserResponse findUserResponseByUserName(String name) {
        Optional<User> user = userRepository.findUserByUserName(name);
        if (!user.isEmpty()) {
            return userToUserResponseMapper.userToResponse(user.get());
        } else {
            throw new NotFoundException(USERS_NOT_FOUND);
        }
    }

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (authorization.authorization(user.getLogin())) {
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            return userRepository.saveAndFlush(user);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public void deleteUserById(int id) {
        if (authorization.authorization(getUser(id).getLogin())) {
            userRepository.deleteUserById(id);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public void addCourseToUser(int userId, int courseId) {
        if (authorization.authorization(getUser(userId).getLogin())) {
            userRepository.addCourseToUser(userId, courseId);
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public List<Course> getCourseForUser(int userId) {
        if (authorization.authorization(getUser(userId).getLogin())) {
            List<Course> courses = userRepository.getCourseForUser(userId);
            return courses;
        } else {
            throw new AccessException(ACCESS_IS_DENIED);
        }
    }

    @Transactional
    public void changeUserPassword(PasswordRequest request) {
        User user = getUser(request.getId());
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            String codePass = passwordEncoder.encode(request.getNewPassword());
            user.setChanged(new Timestamp(System.currentTimeMillis()));
            userRepository.changeUserPassword(request.getId(), codePass);
        } else {
            throw new BadReqException(BAD_PASSWORD);
        }
    }

    private User getUser(int id) {
        return userRepository.findById(id).filter(user -> !user.isDeleted())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}