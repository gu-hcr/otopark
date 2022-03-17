package com.otopark.business.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class ParkingLot extends Persistable {

    private static ParkingLot instance = null;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private Set<ParkingLine> parkingLines = new HashSet<>();

    protected ParkingLot() {

    }

    private synchronized static void createInstance() {
        if (instance == null)
            instance = new ParkingLot();
    }

    public static ParkingLot getInstance() {
        if (instance == null)
            createInstance();
        return instance;
    }

    public void addLine(ParkingLine parkingLine) {
        parkingLines.add(parkingLine);
        parkingLine.setParkingLot(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ParkingLine> getParkingLines() {
        return parkingLines;
    }

    public void setParkingLines(Set<ParkingLine> parkingLines) {
        this.parkingLines = parkingLines;
    }
}
