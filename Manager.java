package com.yorku.parking.model;

public class Manager extends User {
    public void manageParkingLot(ParkingLot lot) {
        System.out.println("Manager managing lot: " + lot.getLotId());
    }
}