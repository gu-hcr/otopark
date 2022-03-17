package com.otopark.business.model;

import javax.persistence.*;

@Entity
@Table
public class VehicleType extends Persistable {

    @Column(name="name", nullable = false)
    private String name;

    /*
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private VehicleCategory vehicleCategory;
*/
    @OneToOne
    private VehicleCategory vehicleCategory;

    public VehicleType() {
    }

    public VehicleType(String name, VehicleCategory vehicleCategory) {
        this.name = name;
        this.vehicleCategory = vehicleCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }
}