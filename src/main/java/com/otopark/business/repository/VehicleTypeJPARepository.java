package com.otopark.business.repository;

import com.otopark.business.model.VehicleType;

import java.util.List;
import java.util.Optional;

public interface VehicleTypeJPARepository extends AbstractJPARepository<VehicleType, Long> {
    Optional<VehicleType> findByName(String name);
    List<VehicleType> findAll();
}