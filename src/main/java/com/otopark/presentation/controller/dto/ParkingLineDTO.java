package com.otopark.presentation.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ParkingLineDTO {
    private int lineOrder;
    private double length;
    private List<ParkedVehicleDTO> vehicles;

    public ParkingLineDTO(int lineOrder, double length) {
        this.lineOrder = lineOrder;
        this.length = length;
        vehicles = new ArrayList<ParkedVehicleDTO>();
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(int lineOrder) {
        this.lineOrder = lineOrder;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public List<ParkedVehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<ParkedVehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }
}