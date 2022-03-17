package com.otopark.business.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Member extends Persistable {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;

    private String licensePlate;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    public Member() {
    }

    public Member(String firstName, String lastName, String phone, String licensePlate, Date startDate, Date endDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    public boolean isValid(){
        Date today = new Date();
        return ( today.compareTo(startDate) >= 0 && today.compareTo(endDate)<=0);
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }
}