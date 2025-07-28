package com.insurancemegacorp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // For demo/upgrade only; not used in main logic
public class Vehicle {

    @Id
    private String id;
    private int year;
    private String make;

    public Vehicle(String id, int year, String make) {
        this.id = id;
        this.year = year;
        this.make = make;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
