package com.tms.lingofriends;

import com.tms.lingofriends.mapper.UserToUserResponseMapper;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.PasswordRequest;
import com.tms.lingofriends.model.response.UserResponse;
import com.tms.lingofriends.repository.UserRepository;
import com.tms.lingofriends.security.Authorization;
import com.tms.lingofriends.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserToUserResponseMapper userToUserResponseMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authorization authorization;
    private com.tms.lingofriends.model.response.PasswordRequest passwordRequest;
    private int id;
    private User user;
    private UserResponse userResponse;
    private final List<User> users = new ArrayList<>();
    private final List<UserResponse> userResponses = new ArrayList<>();
    private final Timestamp time = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init() {
        id = 1;
        user = new User();
        user.setId(1);
        user.setUserName("LizaTest");
        user.setLogin("LoginTest");
        user.setPassword("PasswordTest");
        user.setEmail("test@test.test");
        user.setLanguageName("englishTest");
        user.setRole("USER");
        user.setCreated(time);
        user.setChanged(time);
        user.setDeleted(false);
        users.add(user);
        userResponse = new UserResponse();
        userResponses.add(userResponse);
        passwordRequest = new PasswordRequest();
        passwordRequest.setId(1);
        passwordRequest.setOldPassword("PasswordTest");
        passwordRequest.setNewPassword("NewPasswordTest");
    }

    @Test
    public void getAllUsersResponseTest() {
        when(userRepository.findAll()).thenReturn(users);
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        assertEquals(userResponses, userService.getAllUsersResponse());
    }

    @Test
    public void getUserResponseByIdTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        UserResponse returned = userService.getUserResponseById(id);
        verify(userRepository).findById(id);
        verify(userToUserResponseMapper).userToResponse(user);
        assertEquals(userResponse, returned);
    }

    @Test
    public void findUserResponseByUserNameTest() {
        when(userRepository.findUserByUserName(user.getUserName())).thenReturn(Optional.of(user));
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        UserResponse returned = userService.findUserResponseByUserName(user.getUserName());
        verify(userRepository).findUserByUserName(user.getUserName());
        verify(userToUserResponseMapper).userToResponse(user);
        assertEquals(userResponse, returned);
    }

    @Test
    public void findUserResponseByLanguageNameTest() {
        when(userRepository.findUserByLanguageName(user.getLanguageName())).thenReturn(users);
        when(userToUserResponseMapper.userToResponse(user)).thenReturn(userResponse);
        assertEquals(userResponses, userService.findUserResponseByLanguageName(user.getLanguageName()));
    }

    @Test
    public void createUserTest() {
        user.setCreated(time);
        user.setChanged(time);
        user.setDeleted(false);
        when(passwordEncoder.encode("PasswordTest")).thenReturn("encodedPassword");
        userService.createUser(user);
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void changeUserPasswordTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(passwordRequest.getNewPassword())).thenReturn("NewPasswordTest");
        user.setChanged(new Timestamp(System.currentTimeMillis()));
        userService.changeUserPassword(passwordRequest);
        verify(userRepository).changeUserPassword(passwordRequest.getId(), "NewPasswordTest");
    }

    @Test
    public void updateUserTest() {
        when(authorization.authorization((user.getLogin()))).thenReturn(true);
        user.setChanged(time);
        userService.updateUser(user);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    public void deleteUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(authorization.authorization(user.getLogin())).thenReturn(true);
        userService.deleteUserById(id);
        verify(userRepository).deleteUserById(id);
    }

    @Test
    public void addCourseToUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(authorization.authorization(user.getLogin())).thenReturn(true);
        userService.addCourseToUser(id, id);
        verify(userRepository).addCourseToUser(id, id);
    }

    @Test
    public void getCourseForUserTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(authorization.authorization(user.getLogin())).thenReturn(true);
        userService.getCourseForUser(id);
        verify(userRepository).getCourseForUser(id);
    }
}