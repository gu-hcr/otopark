package com.otopark.presentation.controller;

import com.otopark.business.model.Employee;
import com.otopark.business.service.EmployeeService;
import com.otopark.presentation.controller.dto.EmployeeDTO;
import com.otopark.presentation.controller.dto.EmployeeLookupDTO;
import com.otopark.presentation.controller.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path = "/lookup")
    public ResponseDTO<List<EmployeeLookupDTO>> getEmployeesLookup(){
        List<Employee> employees = employeeService.findAll();
        List<EmployeeLookupDTO> employeeDTOS = new ArrayList<>();
        for (Employee e: employees ) {
            employeeDTOS.add(new EmployeeLookupDTO(e.getId(), e.getFirstName(), e.getLastName()));
        }
        return new ResponseDTO<>("SUCCESS", employeeDTOS);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PER_EMPLOYEES')")
    public ResponseDTO<List<EmployeeDTO>> getEmployees(){
        List<Employee> employees = employeeService.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee e: employees ) {
            employeeDTOS.add(new EmployeeDTO(e));
        }
        return new ResponseDTO<List<EmployeeDTO>>("Employees data has retrieved", employeeDTOS);
    }
}
