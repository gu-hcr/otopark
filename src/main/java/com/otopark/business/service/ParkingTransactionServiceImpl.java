package com.otopark.business.service;

import com.otopark.business.model.ParkingTransaction;
import com.otopark.business.repository.ParkingTransactionJPARepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingTransactionServiceImpl extends AbstractServiceImpl<ParkingTransaction> implements ParkingTransactionService {
    private ParkingTransactionJPARepository parkingTransactionJPARepository;
    public ParkingTransactionServiceImpl(ParkingTransactionJPARepository parkingTransactionJPARepository) {
        super(parkingTransactionJPARepository);
        this.parkingTransactionJPARepository = parkingTransactionJPARepository;
    }
}
