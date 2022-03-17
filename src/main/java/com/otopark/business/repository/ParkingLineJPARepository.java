package com.otopark.business.repository;

import com.otopark.business.model.ParkingLine;

import java.util.Optional;

public interface ParkingLineJPARepository extends AbstractJPARepository<ParkingLine, Long> {
    Optional<ParkingLine> findByLineOrder(int order);
}