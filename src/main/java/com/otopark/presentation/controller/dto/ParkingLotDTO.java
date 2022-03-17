package com.otopark.presentation.controller.dto;

import java.util.List;

public class ParkingLotDTO {
    private List<ParkingLineDTO> lines;

    public ParkingLotDTO(List<ParkingLineDTO> lines) {
        this.lines = lines;
    }

    public List<ParkingLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<ParkingLineDTO> lines) {
        this.lines = lines;
    }
}
