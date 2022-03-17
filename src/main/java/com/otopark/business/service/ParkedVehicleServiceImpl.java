package com.otopark.business.service;

import com.otopark.business.model.ParkedVehicle;
import com.otopark.business.repository.ParkedVehicleJPARepository;
import org.springframework.stereotype.Service;

@Service
public class ParkedVehicleServiceImpl extends AbstractServiceImpl<ParkedVehicle> implements ParkedVehicleService {
    private ParkedVehicleJPARepository parkedVehicleJPARepository;
    public ParkedVehicleServiceImpl(ParkedVehicleJPARepository parkedVehicleJPARepository) {
        super(parkedVehicleJPARepository);
        this.parkedVehicleJPARepository = parkedVehicleJPARepository;
    }


}
