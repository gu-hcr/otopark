package com.otopark.business.service;

import com.otopark.business.model.Employee;

import java.util.Optional;


public interface EmployeeService extends AbstractService<Employee> {
    Optional<Employee> findByIdentityNumber(String identityNuber);
}

