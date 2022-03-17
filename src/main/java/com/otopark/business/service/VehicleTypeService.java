package com.otopark.business.service;

import com.otopark.business.model.VehicleType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VehicleTypeService extends AbstractService<VehicleType>{
    Optional<VehicleType> findByName(String name);
}
