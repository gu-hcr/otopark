package com.otopark.business.service;

import com.otopark.business.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user == null) {
                throw new UsernameNotFoundException("Could not find user");
            }
            return user;
        } else {
            throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        }
    }
}