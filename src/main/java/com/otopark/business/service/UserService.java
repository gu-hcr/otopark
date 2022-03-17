package com.otopark.business.service;

import com.otopark.business.model.User;

import java.util.Optional;

public interface UserService extends AbstractService<User>{
    Optional<User> findByUsername(String username);
    boolean isUsernameExist(String username);
    User setPassword(User user, String rawPassword);
}
