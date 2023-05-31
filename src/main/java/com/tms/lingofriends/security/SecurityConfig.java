package com.tms.lingofriends.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailService userDetailService;

    @Autowired
    public SecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/user/res/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/create").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/user/update").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/user/changePass").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/course/res/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/course/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/course/create").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/course/update").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/course/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/lesson/res/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/lesson/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/lesson/create").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/lesson/update").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/lesson/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/subscription/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/subscription/res/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/subscription/us/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/subscription/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/subscription/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/subscription/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .userDetailsService(userDetailService)
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}