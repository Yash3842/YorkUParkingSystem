package com.yorku.parking.model;

public class Sensor implements ISensor {
    private String sensorId;

    public Sensor(String sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public boolean detectCarPresence() {
        return Math.random() > 0.5; // Simulated detection
    }

    @Override
    public String scanLicensePlate() {
        return "XYZ123"; // Simulated plate
    }
}