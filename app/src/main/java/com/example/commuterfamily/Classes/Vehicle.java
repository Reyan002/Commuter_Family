package com.example.commuterfamily.Classes;

public class Vehicle {
    private String VehicleType;
    private String VehicleNumber;

    public Vehicle() {
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public Vehicle(String vehicleType, String vehicleNumber) {
        VehicleType = vehicleType;
        VehicleNumber = vehicleNumber;
    }
}
