package com.otopark.business.service;

import com.otopark.business.model.Privilege;

import java.util.Optional;

public interface PrivilegeService extends AbstractService<Privilege> {
    Optional<Privilege> findByName(String name);
}

