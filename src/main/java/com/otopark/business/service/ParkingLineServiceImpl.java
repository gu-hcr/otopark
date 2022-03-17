package com.otopark.business.service;

import com.otopark.business.model.ParkedVehicle;
import com.otopark.business.model.ParkingLine;
import com.otopark.business.repository.ParkingLineJPARepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ParkingLineServiceImpl extends AbstractServiceImpl<ParkingLine> implements ParkingLineService {
    private ParkingLineJPARepository parkingLineJPARepository;
    public ParkingLineServiceImpl(ParkingLineJPARepository parkingLineJPARepository) {
        super(parkingLineJPARepository);
        this.parkingLineJPARepository = parkingLineJPARepository;
    }

    @Override
    public Optional<ParkingLine> findByLineOrder(int order) {
        return parkingLineJPARepository.findByLineOrder(order);
    }

    @Override
    @Transactional
    public void removeVehicle(  ParkedVehicle v ){
        ParkingLine l = v.getParkingLine();
        l.getVehicles().remove(v);
        save(l);
    }

    @Override
    @Transactional
    public void addVehicle( ParkingLine l, ParkedVehicle v ){
        l.getVehicles().add(v);
        save(l);
    }
}
