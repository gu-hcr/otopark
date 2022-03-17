package com.otopark.business.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
public class ParkingTransaction extends Persistable {
    @ManyToOne()
    @JoinColumn(name= "parking_line_id")
    private ParkingLine parkingLine;

    @ManyToOne
    @JoinColumn( nullable = false)
    private VehicleType vehicleType;

    @Column( nullable = false)
    private String licensePlate;

    @Column( nullable = false)
    private Date enterTime;

    private Date exitTime;

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee responsibleEmployee;

    @Column(nullable = false)
    private Double positionStart;

    @Column(nullable = false)
    private Double positionEnd;

    private Double price;

    public ParkingLine getParkingLine() {
        return parkingLine;
    }

    public void setParkingLine(ParkingLine parkingLine) {
        this.parkingLine = parkingLine;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Employee getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee(Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMemberFullName(){
        return (member != null ? member.getFullName() : "");
    }

    public String getResponsibleFullName(){
        return (responsibleEmployee != null ? responsibleEmployee.getFullName() : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParkingTransaction that = (ParkingTransaction) o;
        return parkingLine.equals(that.parkingLine) && vehicleType.equals(that.vehicleType) && licensePlate.equals(that.licensePlate) && enterTime.equals(that.enterTime) && Objects.equals(exitTime, that.exitTime) && Objects.equals(member, that.member) && responsibleEmployee.equals(that.responsibleEmployee) && positionStart.equals(that.positionStart) && positionEnd.equals(that.positionEnd);
    }

}
