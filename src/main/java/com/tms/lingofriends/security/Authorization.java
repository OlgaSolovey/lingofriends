package com.tms.lingofriends.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Authorization {

    public boolean authorization(String login) {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(login);
    }
}