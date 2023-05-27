package com.tms.lingofriends.mapper;

import com.tms.lingofriends.model.User;
import com.tms.lingofriends.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapper {
    public UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName(user.getUserName());
        userResponse.setLanguageName(user.getLanguageName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}