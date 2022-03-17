package com.otopark.business.model;

import javax.persistence.*;

@Entity
@Table
public class VehicleCategory extends Persistable {

    @Column(name="name", nullable = false)
    private String name;

    private int categoryOrder;

    private double coefficient;

    private double price;

    public VehicleCategory() {
    }

    public VehicleCategory(String name, int categoryOrder, double coefficient, double price) {
        this.name = name;
        this.categoryOrder = categoryOrder;
        this.coefficient = coefficient;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(int categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}