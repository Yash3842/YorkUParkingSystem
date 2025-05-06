package com.yorku.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String lotId;
    private String location;
    public static final int MAX_SPACES = 100;
    private List<IParkingSpace> spaces = new ArrayList<>();

    public ParkingLot(String lotId, String location) {
        this.lotId = lotId;
        this.location = location;
    }

    public void enableLot() {
        System.out.println("Lot enabled: " + lotId);
    }

    public void disableLot() {
        System.out.println("Lot disabled: " + lotId);
    }

    public void addSpace(IParkingSpace space) {
        if (spaces.size() < MAX_SPACES) {
            spaces.add(space);
        }
    }

    public List<IParkingSpace> getSpaces() {
        return spaces;
    }

    public String getLotId() {
        return lotId;
    }

    // ✅ Add this to fix the error in UserManager
    public String getName() {
        return lotId;
    }

    public String getLocation() {
        return location;
    }
}
