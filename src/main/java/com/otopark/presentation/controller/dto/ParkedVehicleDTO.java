package com.otopark.presentation.controller.dto;

import java.util.Date;

public class ParkedVehicleDTO {
    private Long id;
    private String typeName;
    private String categoryName;
    private int lineOrder;
    private Double positionStart;
    private Double positionEnd;
    private String licensePlate;
    private Date startTime;
    private Date endTime;
    private boolean isMember;
    private String memberName;

    public ParkedVehicleDTO() {
    }

    public ParkedVehicleDTO(Long id, String typeName, String categoryName, int lineOrder, Double positionStart, Double positionEnd, String licensePlate, Date startTime, Date endTime, boolean isMember, String memberName) {
        this.id = id;
        this.typeName = typeName;
        this.categoryName = categoryName;
        this.lineOrder = lineOrder;
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.licensePlate = licensePlate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isMember = isMember;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(int lineOrder) {
        this.lineOrder = lineOrder;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(Double positionStart) {
        this.positionStart = positionStart;
    }

    public Double getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(Double positionEnd) {
        this.positionEnd = positionEnd;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
