package com.otopark.presentation.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.otopark.business.model.*;
import com.otopark.business.service.*;
import com.otopark.presentation.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/parking-lot")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ParkingLineDTO>>> getParkingLot(){
        ParkingLot parkingLot = parkingLotService.getInstance();
        List<ParkingLineDTO> parkingLineDTOList = new ArrayList<ParkingLineDTO>();

        for (ParkingLine l: parkingLot.getParkingLines()) {

            ParkingLineDTO lineDTO = new ParkingLineDTO(l.getLineOrder(), l.getLength() );
            List<ParkedVehicle> sortedV = l.getVehicles().stream().sorted(Comparator.comparing(x -> x.getParkingTransaction().getPositionStart())).collect(Collectors.toList());

            for (ParkedVehicle v: sortedV) {

                lineDTO.getVehicles().add(

                        new ParkedVehicleDTO(
                                v.getId(),
                                v.getParkingTransaction().getVehicleType().getName(),
                                v.getParkingTransaction().getVehicleType().getVehicleCategory().getName(),
                                v.getParkingLine().getLineOrder(),
                                v.getParkingTransaction().getPositionStart(),
                                v.getParkingTransaction().getPositionEnd(),
                                v.getParkingTransaction().getLicensePlate(),
                                v.getParkingTransaction().getEnterTime(),
                                v.getParkingTransaction().getExitTime(),
                                v.getParkingTransaction().getMember()!= null,
                                (v.getParkingTransaction().getMember()!=null ? v.getParkingTransaction().getMember().getFullName():"")
                                )
                );
            }
            parkingLineDTOList.add(lineDTO);
        }
        ResponseDTO<List<ParkingLineDTO>> responseDTO = new ResponseDTO<List<ParkingLineDTO>>("Parking lot data has retrieved", parkingLineDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping(path="/vehicles/check-in")
    @PreAuthorize("hasAnyAuthority('ROLE_OPRT','ROLE_ADMIN') ")
    public ResponseEntity<?> vehicleCheckIn(@RequestBody CheckInRequestDTO checkInRequestDTO) {
        String typeName = checkInRequestDTO.getTypeName();
        String licensePlate = checkInRequestDTO.getLicensePlate();
        Long employeeId =checkInRequestDTO.getEmployeeId();

        ParkedVehicle parkedVehicle = parkingLotService.vehicleCheckin(typeName,licensePlate, employeeId);

        if(parkedVehicle != null)
            return ResponseEntity.status(HttpStatus.OK).body( new ResponseDTO<String>("Vehicle is checked in", ""));
        else
            return ResponseEntity.status(422).body(new ResponseDTO<String>("There is no suitable room for this vehicle",""));
    }

    @GetMapping(path="/vehicles/{id}/price")
    @PreAuthorize("hasAnyAuthority('ROLE_OPRT','ROLE_ADMIN') ")
    public ResponseEntity<ResponseDTO<Double>>  vehicleCheckOutPrice(@PathVariable("id") Long id){
        Double totalPrice = parkingLotService.getVehiclePrice(id);
        return ResponseEntity.status(HttpStatus.OK).body( new ResponseDTO<Double>("Cost has calculated", totalPrice));
    }

    @PostMapping(path="/vehicles/{id}/check-out")
    @PreAuthorize("hasAnyAuthority('ROLE_OPRT','ROLE_ADMIN') ")
    public ResponseEntity<?> vehicleCheckOut(@PathVariable("id") Long id, @RequestBody ObjectNode json){
        try {
            parkingLotService.vehicleCheckout(id, json.get("price").asDouble());
        }
        catch (Exception e) {
            return ResponseEntity.status(422).body(new ResponseDTO<String>(e.getMessage(),""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<String>("Vehicle is checked out", ""));

    }
}
