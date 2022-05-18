package com.example.carrentaldesktop;

public class Car {
    private int id;
    private String license_plate_number;
    private String brand;
    private String model;
    private int daily_cost;

    public Car(int id, String license_plate_number, String brand, String model, int daily_cost) {
        this.id = id;
        this.license_plate_number = license_plate_number;
        this.brand = brand;
        this.model = model;
        this.daily_cost = daily_cost;
    }

    public int getId() {
        return id;
    }

    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getDaily_cost() {
        return daily_cost;
    }
}
