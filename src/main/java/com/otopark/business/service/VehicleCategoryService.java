package com.otopark.business.service;

import com.otopark.business.model.VehicleCategory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VehicleCategoryService extends AbstractService<VehicleCategory>{
    Optional<VehicleCategory> findByName(String name);
    Optional<VehicleCategory> findByCategoryOrder(int order);
}
