package com.otopark.presentation.controller.dto;

import com.otopark.business.model.Member;

import java.util.Date;

public class MemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String licensePlate;
    private Date startDate;
    private Date endDate;

    public MemberDTO(Member m){
        this.id = m.getId();
        this.firstName = m.getFirstName();
        this.lastName = m.getLastName();
        this.phone = m.getPhone();
        this.licensePlate = m.getLicensePlate();
        this.startDate = m.getStartDate();
        this.endDate = m.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
