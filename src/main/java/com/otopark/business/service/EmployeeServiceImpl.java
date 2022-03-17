package com.otopark.business.service;

import com.otopark.business.model.Employee;
import com.otopark.business.repository.EmployeeJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl extends AbstractServiceImpl<Employee> implements EmployeeService{
    EmployeeJPARepository employeeJPARepository;
    public EmployeeServiceImpl(EmployeeJPARepository employeeJPARepository) {
        super(employeeJPARepository);
        this.employeeJPARepository = employeeJPARepository;
    }

    @Override
    public Optional<Employee> findByIdentityNumber(String identityNumber) {
        return employeeJPARepository.findByIdentityNumber(identityNumber);
    }
}
