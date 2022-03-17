package com.otopark.presentation.controller;

import com.otopark.business.model.ParkingTransaction;
import com.otopark.business.service.ParkingTransactionService;
import com.otopark.presentation.controller.dto.ResponseDTO;
import com.otopark.presentation.controller.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/transactions")
public class ParkingTransactionController {

    @Autowired
    ParkingTransactionService parkingTransactionService;

    @GetMapping
    @PreAuthorize("hasAuthority('PER_TRANSACTIONS')")
    public ResponseDTO<List<TransactionDTO>> getTransactions(){
        List<ParkingTransaction> transactions = parkingTransactionService.findAll();
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (ParkingTransaction t: transactions ) {
            transactionDTOS.add(new TransactionDTO(t));
        }
        return new ResponseDTO<List<TransactionDTO>>("Transactions data has retrieved", transactionDTOS);
    }
}
