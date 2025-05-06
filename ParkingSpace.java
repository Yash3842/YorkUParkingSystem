package com.yorku.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpace implements IParkingSpace {
    private String spaceId;
    private String status = "Available";
    private Sensor sensor;
    private boolean occupied = false;
    private List<Observer> observers = new ArrayList<>();

    public ParkingSpace(String spaceId, Sensor sensor) {
        this.spaceId = spaceId;
        this.sensor = sensor;
    }

    @Override
    public String getId() {
        return spaceId; // ✅ This is the fix
    }

    @Override
    public String getDescription() {
        return "Basic Parking Space " + spaceId;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void enableSpace() {
        status = "Available";
        notifyObservers();
    }

    @Override
    public void disableSpace() {
        status = "Disabled";
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
    
    @Override
    public void markOccupied() {
        this.occupied = true;
        this.status = "Occupied";
        notifyObservers();
    }

    @Override
    public boolean isOccupied() {
        return occupied;
    }
}
