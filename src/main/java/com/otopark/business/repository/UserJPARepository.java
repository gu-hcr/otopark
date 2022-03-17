package com.otopark.business.repository;

import com.otopark.business.model.User;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends AbstractJPARepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAll();
}