package com.otopark.business.service;

import com.otopark.business.model.Privilege;
import com.otopark.business.repository.PrivilegeJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeServiceImpl extends AbstractServiceImpl<Privilege> implements PrivilegeService {
    PrivilegeJPARepository privilegeJPARepository;

    public PrivilegeServiceImpl(PrivilegeJPARepository privilegeJPARepository) {
        super(privilegeJPARepository);
        this.privilegeJPARepository = privilegeJPARepository;
    }

    @Override
    public Optional<Privilege> findByName(String name) {
        return privilegeJPARepository.findByName(name);
    }
}
