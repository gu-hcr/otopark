package com.otopark.business.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Privilege extends Persistable{

    private String name;

    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}