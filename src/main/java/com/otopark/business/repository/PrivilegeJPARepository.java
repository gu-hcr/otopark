package com.otopark.business.repository;

import com.otopark.business.model.Privilege;

import java.util.Optional;

public interface PrivilegeJPARepository extends AbstractJPARepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
}