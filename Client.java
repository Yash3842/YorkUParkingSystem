package com.yorku.parking.model;

public abstract class Client extends User {
    private String userType;
    private String licensePlate;

    public abstract Booking bookParking(IParkingSpace space);
    public abstract double getHourlyRate();

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}