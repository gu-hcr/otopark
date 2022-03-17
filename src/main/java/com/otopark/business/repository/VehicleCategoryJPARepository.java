package com.otopark.business.repository;

import com.otopark.business.model.VehicleCategory;

import java.util.Optional;

public interface VehicleCategoryJPARepository extends AbstractJPARepository<VehicleCategory, Long> {
    Optional<VehicleCategory> findByName(String name);
    Optional<VehicleCategory> findByCategoryOrder(int order);
}