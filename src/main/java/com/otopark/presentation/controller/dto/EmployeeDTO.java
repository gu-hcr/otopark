package com.otopark.presentation.controller.dto;

import com.otopark.business.model.Employee;

import java.util.Date;

public class EmployeeDTO {
    private Long id;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private Date startDate;
    private Date endDate;

    public EmployeeDTO(Employee e){
        this.id = e.getId();
        this.identityNumber= e.getIdentityNumber();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.startDate = e.getStartDate();
        this.endDate = e.getEndDate();
        this.phone = e.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
