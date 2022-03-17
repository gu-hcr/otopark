package com.otopark.business.service;

import com.otopark.business.model.ParkedVehicle;
import com.otopark.business.model.ParkingLot;

public interface ParkingLotService extends AbstractService<ParkingLot> {
    ParkingLot getInstance();
    Double getVehiclePrice(Long vehicleId);
    void vehicleCheckout(Long vehicleId, Double price);
    ParkedVehicle vehicleCheckin(String vehicleTypeName, String licensePlate, Long employeeId);
}
