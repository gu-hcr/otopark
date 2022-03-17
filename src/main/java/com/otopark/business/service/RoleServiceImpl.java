package com.otopark.business.service;

import com.otopark.business.model.Role;
import com.otopark.business.repository.RoleJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role> implements RoleService{
    private final RoleJPARepository roleJPARepository;

    protected RoleServiceImpl(RoleJPARepository roleJPARepository) {
        super(roleJPARepository);
        this.roleJPARepository = roleJPARepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJPARepository.findByName(name);
    }
}
