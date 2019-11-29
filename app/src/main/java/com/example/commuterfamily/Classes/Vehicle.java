package com.example.commuterfamily.Classes;

public class Vehicle {
    private String VehicleType;
    private String VehicleNumber;
    private String DriverCnic;
    private String DriverLicence;
    private String VehicleDocuments;
    private String date;
    private String time;
    private String vid;


    public Vehicle() {
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public void setDriverCnic(String driverCnic) {
        DriverCnic = driverCnic;
    }

    public void setDriverLicence(String driverLicence) {
        DriverLicence = driverLicence;
    }

    public void setVehicleDocuments(String vehicleDocuments) {
        VehicleDocuments = vehicleDocuments;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDriverCnic() {
        return DriverCnic;
    }

    public String getDriverLicence() {
        return DriverLicence;
    }

    public String getVehicleDocuments() {
        return VehicleDocuments;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getVid() {
        return vid;
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
