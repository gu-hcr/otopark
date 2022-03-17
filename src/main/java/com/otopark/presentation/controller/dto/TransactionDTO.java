package com.otopark.presentation.controller.dto;

import com.otopark.business.model.*;

import java.util.Date;

public class TransactionDTO {
    private Long id;
    private String vehicleType;
    private String vehicleCategory;
    private String licensePlate;
    private int lineOrder;
    private Double positionStart;
    private Date enterTime;
    private Date exitTime;
    private String memberName;
    private String responsibleName;
    private Double price;

    public TransactionDTO(ParkingTransaction pt) {
        this.id = pt.getId();
        this.vehicleType = pt.getVehicleType().getName();
        this.vehicleCategory = pt.getVehicleType().getVehicleCategory().getName();
        this.licensePlate = pt.getLicensePlate();
        this.lineOrder = pt.getParkingLine().getLineOrder();
        this.positionStart = pt.getPositionStart();
        this.enterTime = pt.getEnterTime();
        this.exitTime = pt.getExitTime();
        this.memberName = pt.getMemberFullName();
        this.responsibleName = pt.getResponsibleFullName();
        this.price = pt.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public Double getPositionStart() {
        return positionStart;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public Double getPrice() {
        return price;
    }
}
