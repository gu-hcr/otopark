package com.otopark.business.model;

import javax.persistence.*;

@Entity
@Table
public class ParkedVehicle extends Persistable{
    @ManyToOne
    private ParkingLine parkingLine;

    @OneToOne
    private ParkingTransaction parkingTransaction;

    private String note;

    public ParkedVehicle() {
    }

    public ParkedVehicle(ParkingTransaction parkingTransaction) {
        this.parkingTransaction = parkingTransaction;
    }

    public ParkedVehicle(ParkingTransaction parkingTransaction, ParkingLine parkingLine) {
        this.parkingTransaction = parkingTransaction;
        this.parkingLine = parkingLine;
    }

    public ParkingLine getParkingLine() {
        return parkingLine;
    }

    public void setParkingLine(ParkingLine parkingLine) {
        this.parkingLine = parkingLine;
    }

    public ParkingTransaction getParkingTransaction() {
        return parkingTransaction;
    }

    public void setParkingTransaction(ParkingTransaction parkingTransaction) {
        this.parkingTransaction = parkingTransaction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
