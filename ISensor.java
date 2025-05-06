package com.yorku.parking.model;

public interface ISensor {
    boolean detectCarPresence();
    String scanLicensePlate();
}