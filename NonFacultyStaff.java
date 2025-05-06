package com.yorku.parking.model;

import java.util.Date;

public class NonFacultyStaff extends Client {
    public static final double HOURLY_RATE = 10.0;

    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }

    @Override
    public Booking bookParking(IParkingSpace space) {
        Booking booking = new Booking();
        booking.setStartTime(new Date());
        System.out.println("NonFacultyStaff booked space: " + space.getDescription());
        return booking;
    }
}