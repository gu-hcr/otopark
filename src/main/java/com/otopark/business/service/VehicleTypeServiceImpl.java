package com.otopark.business.service;

import com.otopark.business.model.VehicleType;
import com.otopark.business.repository.VehicleTypeJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleTypeServiceImpl extends AbstractServiceImpl<VehicleType> implements VehicleTypeService {

    private final VehicleTypeJPARepository vehicleTypeJPARepository;

    public VehicleTypeServiceImpl(VehicleTypeJPARepository vehicleTypeJPARepository) {
        super(vehicleTypeJPARepository);
        this.vehicleTypeJPARepository = vehicleTypeJPARepository;
    }

    @Override
    public Optional<VehicleType> findByName(String name) {
        return vehicleTypeJPARepository.findByName(name);
    }
}
