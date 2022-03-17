package com.otopark.business.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class ParkingLine extends Persistable {
    private int lineOrder;
    private Double length;

    @ManyToOne
    @JoinColumn(name= "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @OneToMany( mappedBy = "parkingLine" , cascade = CascadeType.ALL, fetch = FetchType.EAGER ,orphanRemoval=true)
    private Set<ParkedVehicle> vehicles = new HashSet<>();

    public ParkingLine() {
    }

    public ParkingLine( int lineOrder, Double length) {
        this.lineOrder = lineOrder;
        this.length = length;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(int lineOrder) {
        this.lineOrder = lineOrder;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Set<ParkedVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<ParkedVehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(ParkedVehicle parkedVehicle){
        vehicles.add(parkedVehicle);
        parkedVehicle.setParkingLine(this);
    }

}
