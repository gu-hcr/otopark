package com.otopark.business.service;

import com.otopark.business.model.Role;

import java.util.Optional;

public interface RoleService extends AbstractService<Role> {
    Optional<Role> findByName(String name);
}

