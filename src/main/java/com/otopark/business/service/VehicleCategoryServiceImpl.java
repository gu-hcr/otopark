package com.otopark.business.service;

import com.otopark.business.model.VehicleCategory;
import com.otopark.business.repository.VehicleCategoryJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCategoryServiceImpl extends AbstractServiceImpl<VehicleCategory> implements VehicleCategoryService {

    private final VehicleCategoryJPARepository vehicleCategoryJPARepository;

    public VehicleCategoryServiceImpl(VehicleCategoryJPARepository vehicleCategoryJPARepository) {
        super(vehicleCategoryJPARepository);
        this.vehicleCategoryJPARepository = vehicleCategoryJPARepository;
    }

    @Override
    public Optional<VehicleCategory> findByName(String name) {
        return vehicleCategoryJPARepository.findByName(name);
    }

    @Override
    public Optional<VehicleCategory> findByCategoryOrder(int order) {
        return vehicleCategoryJPARepository.findByCategoryOrder(order);
    }
}
