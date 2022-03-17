package com.otopark.business.repository;

import com.otopark.business.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleJPARepository extends AbstractJPARepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findAll();
}