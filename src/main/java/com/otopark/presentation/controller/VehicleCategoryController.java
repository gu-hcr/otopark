package com.otopark.presentation.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.otopark.business.model.VehicleCategory;
import com.otopark.business.service.VehicleCategoryService;
import com.otopark.presentation.controller.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/vehicle-catogories")
public class VehicleCategoryController {

    @Autowired
    private VehicleCategoryService vehicleCategoryService;

    @GetMapping(path = "/base-price")
    public ResponseEntity<ResponseDTO<Double>> getBasePrice(){
        Optional<VehicleCategory> vc = vehicleCategoryService.findByName("BASE");
        if(vc.isPresent())
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>("Base price has retrieved", vc.get().getPrice() ));
        else
            return ResponseEntity.status(422).body(new ResponseDTO<>("Base price definition is not exist!!", vc.get().getPrice() ));
    }

    //@Secured("ROLE_ADMIN")
    @PostMapping(path = "/base-price")
    @PreAuthorize("hasAuthority('PER_MODIFY_BASE_PRICE')")
    public ResponseEntity<ResponseDTO<String>> updateParkingLot(@RequestBody ObjectNode json){
        Optional<VehicleCategory> optionelVc = vehicleCategoryService.findByName("BASE");

        if(optionelVc.isPresent()) {
            VehicleCategory vc = optionelVc.get();
            vc.setPrice(json.get("price").asDouble());
            vehicleCategoryService.save(vc);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<String>("Base price is updated", ""));
        }
            else
            return ResponseEntity.status(422).body(new ResponseDTO<String>("Base price definition is not exist!!", "" ));
    }

 }
