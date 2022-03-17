package com.otopark.business.service;

import com.otopark.business.model.ParkedVehicle;
import com.otopark.business.model.ParkingLine;

import java.util.Optional;

public interface ParkingLineService extends AbstractService<ParkingLine> {
    Optional<ParkingLine> findByLineOrder(int order);
    void removeVehicle(ParkedVehicle v);
    void addVehicle(ParkingLine l, ParkedVehicle v);
}
