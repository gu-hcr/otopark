package com.otopark.business.repository;

import com.otopark.business.model.Employee;

import java.util.Optional;

public interface EmployeeJPARepository extends AbstractJPARepository<Employee, Long> {
    Optional<Employee> findByFirstName(String firstName);
    Optional<Employee> findByLastName(String lastName);
    Optional<Employee> findByIdentityNumber(String identityNumber);
}